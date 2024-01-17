using Cafe.Service.Models.Authentication;
using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Operator;
using Cafe.Service.Models.Order;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Cafe.Service.Models.Versions;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using System;
using System.Security.Claims;
using System.Security.Cryptography;
using System.Text;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.
builder.Services.AddControllersWithViews();

//builder.Services.AddTransient
//builder.Services.AddScoped
//builder.Services.AddSingleton

string connection = builder.Configuration.GetConnectionString("DefaultConnection");
builder.Services.AddDbContext<ApplicationContext>(
    options => options.UseSqlServer(connection)); //singleton -> transient???
//TODO version db

//отдельный метод или приложение
builder.Services.AddTransient<CategoryRepository>();
builder.Services.AddTransient<MenuItemRepository>();
builder.Services.AddTransient<OperatorRepository>();
builder.Services.AddTransient<OrderDetailsRepository>();
builder.Services.AddTransient<OrderRepository>();
builder.Services.AddTransient<TableRepository>();
builder.Services.AddTransient<TableVersionRepository>();
builder.Services.AddTransient<TableProvider>();
builder.Services.AddTransient<OrderProvider>();
builder.Services.AddTransient<MenuItemProvider>();
builder.Services.AddTransient<OperatorProvider>();
builder.Services.AddTransient<CategoryProvider>();
builder.Services.AddTransient<UserRepository>();

builder.Services.AddSwaggerGen();

builder.Services
    .AddAuthentication(CookieAuthenticationDefaults.AuthenticationScheme)
    .AddCookie(options => options.LoginPath = "/login");
builder.Services.AddAuthorization();

var app = builder.Build();

// Configure the HTTP request pipeline.
if (!app.Environment.IsDevelopment())
{
    app.UseExceptionHandler("/Home/Error");
    app.UseHsts();
}

app.UseHttpsRedirection();

app.UseStaticFiles();

app.UseRouting();

app.UseAuthentication();
app.UseAuthorization();

app.Use(async (context, next) =>
{
    bool isAdmin = context.User.HasClaim(ClaimTypes.Role, UserRepository.AdminRole);
    context.Items.Add("isAdmin", isAdmin);
    await next();
});

app.MapControllerRoute(
    name: "default",
    pattern: "{controller=Order}/{action=Index}/{id?}");

app.UseSwagger();
app.UseSwaggerUI();

app.MapGet("/login", async (HttpContext context) =>
{
    context.Response.ContentType = "text/html; charset=utf-8";
    // html-форма для ввода логина/пароля
    string loginForm = @"<!DOCTYPE html>
    <html>
    <head>
        <meta charset='utf-8' />
        <title></title>
    </head>
    <body>
        <h2>Login Form</h2>
        <form method='post'>
            <p>
                <label>Email</label><br />
                <input name='email' />
            </p>
            <p>
                <label>Password</label><br />
                <input type='password' name='password' />
            </p>
            <input type='submit' value='Login' />
        </form>
    </body>
    </html>";
    await context.Response.WriteAsync(loginForm);
});

app.MapPost("/login", async (string? returnUrl, HttpContext context, UserRepository userRepository) =>
{
    // получаем из формы email и пароль
    var form = context.Request.Form;
    // если email и/или пароль не установлены, посылаем статусный код ошибки 400
    if (!form.ContainsKey("email") || !form.ContainsKey("password"))
        return Results.BadRequest("Email и/или пароль не установлены");

    string email = form["email"];

    string password = form["password"];
    //var xx = HashPassword(form["password"]);
    // находим пользователя 
    Person? person = null;

    foreach (var p in userRepository.Get())
    {
        if(p.Email == email && VerifyHashedPassword(p.Password, password))
        {
            person = p;
            break;
        }
    }
    
    // если пользователь не найден, отправляем статусный код 401
    if (person is null) 
        return Results.Unauthorized();

    var claims = new List<Claim> 
    { 
        new Claim(ClaimTypes.Name, person.Email),
        new Claim(ClaimTypes.Role, person.Email == "admin@cafe.app" ? UserRepository.AdminRole : UserRepository.OperatorRole)
    };

    // создаем объект ClaimsIdentity
    ClaimsIdentity claimsIdentity = new ClaimsIdentity(claims, "Cookies");
    // установка аутентификационных куки
    await context.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, new ClaimsPrincipal(claimsIdentity));
    return Results.Redirect(returnUrl ?? "/");
});

static string HashPassword(string password)
{
    byte[] salt;
    byte[] buffer2;
    if (password == null)
    {
        throw new ArgumentNullException("password");
    }
    using (Rfc2898DeriveBytes bytes = new Rfc2898DeriveBytes(password, 0x10, 0x3e8))
    {
        salt = bytes.Salt;
        buffer2 = bytes.GetBytes(0x20);
    }
    byte[] dst = new byte[0x31];
    Buffer.BlockCopy(salt, 0, dst, 1, 0x10);
    Buffer.BlockCopy(buffer2, 0, dst, 0x11, 0x20);
    return Convert.ToBase64String(dst);
}

static bool VerifyHashedPassword(string hashedPassword, string password)
{
    byte[] buffer4;
    if (hashedPassword == null)
    {
        return false;
    }
    if (password == null)
    {
        throw new ArgumentNullException("password");
    }
    byte[] src = Convert.FromBase64String(hashedPassword);
    if ((src.Length != 0x31) || (src[0] != 0))
    {
        return false;
    }
    byte[] dst = new byte[0x10];
    Buffer.BlockCopy(src, 1, dst, 0, 0x10);
    byte[] buffer3 = new byte[0x20];
    Buffer.BlockCopy(src, 0x11, buffer3, 0, 0x20);
    using (Rfc2898DeriveBytes bytes = new Rfc2898DeriveBytes(password, dst, 0x3e8))
    {
        buffer4 = bytes.GetBytes(0x20);
    }
    return buffer3.SequenceEqual(buffer4);
}

app.MapGet("/logout", async (HttpContext context) =>
{
    await context.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
    return Results.Redirect("/");
});

app.Run();
