using Cafe.Service.Models.Authentication;
using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Order;
using Cafe.Service.Models.Versions;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Shared
{
    public class ApplicationContext : DbContext
    {
        public DbSet<Category> Categories { get; set; }
        public DbSet<MenuItem> MenuItems { get; set; }
        public DbSet<Operator.Operator> Operators { get; set; }
        public DbSet<Order.Order> Orders { get; set; }
        public DbSet<OrderDetails> OrderDetails { get; set; }
        public DbSet<Table.Table> Tables { get; set; }
        public DbSet<TableVersion> TableVersions { get; set; }
        public DbSet<Person> Persons { get; set; }

        public ApplicationContext(DbContextOptions<ApplicationContext> options) : base(options)
        {
            //Database.EnsureDeleted();
            Database.EnsureCreated(); // создаем базу данных при первом обращении
        }
    }
}
