using Cafe.Service.Models.Authentication;
using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Table;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;
using System.Security.Claims;

namespace Cafe.Service.Controllers
{
    [Authorize]
    public class MenuItemController : Controller
    {
        private IWebHostEnvironment _environment;
        private MenuItemProvider _menuItemProvider;
        private CategoryProvider _categoryProvider;

        public MenuItemController(
            IWebHostEnvironment environment,
            MenuItemProvider menuItemProvider,
            CategoryProvider categoryProvider)
        {
            _environment = environment;
            _menuItemProvider = menuItemProvider;
            _categoryProvider = categoryProvider;
        }

        public ActionResult Index()
        {            
            return View(_menuItemProvider.Get());
        }

        public ActionResult Add()
        {
            SelectList categories = new SelectList(_categoryProvider.Get().Items.Select(x => x.Title));
            ViewBag.Categories = categories;
            return View();
        }

        [HttpPost]
        public ActionResult AddSave(MenuItem menuItem)
        {
            string path = Path.Combine(_environment.WebRootPath, "images", menuItem.ImageFile.FileName);
            using (var fileStream = new FileStream(path, FileMode.Create))
            {
                menuItem.ImageFile.CopyTo(fileStream);
            }
            menuItem.Image = Path.Combine("images", menuItem.ImageFile.FileName);

            var category = _categoryProvider.Get().Items.FirstOrDefault(x => x.Title == menuItem.Category.Title);
            menuItem.Category = category;
            _menuItemProvider.Add(menuItem);
            return RedirectToAction("Index", "MenuItem");
        }

        public ActionResult Update(MenuItem menuItem)
        {
            var item = _menuItemProvider.Get(menuItem.Id);
            SelectList categories = new SelectList(_categoryProvider.Get().Items.Select(x => x.Title), item.Category.Title);
            ViewBag.Categories = categories;

            string path = Path.Combine(_environment.WebRootPath, item.Image);
            using (var stream = System.IO.File.OpenRead(path))
            {
                item.ImageFile = new FormFile(stream, 0,
                    stream.Length, null, Path.GetFileName(stream.Name));
            }

            return View(item);
        }

        [HttpPost]
        public ActionResult UpdateSave(MenuItem menuItem)
        {
            string path = Path.Combine(_environment.WebRootPath, "images", menuItem.ImageFile.FileName);
            using (var fileStream = new FileStream(path, FileMode.Create))
            {
                menuItem.ImageFile.CopyTo(fileStream);
            }
            menuItem.Image = Path.Combine("images", menuItem.ImageFile.FileName);

            var category = _categoryProvider.Get().Items.FirstOrDefault(x => x.Title == menuItem.Category.Title);
            menuItem.CategoryId = category.Id;
            _menuItemProvider.Edit(menuItem);
            return RedirectToAction("Index", "MenuItem");
        }

        public ActionResult Delete(MenuItem menuItem)
        {
            var item = _menuItemProvider.Get(menuItem.Id);
            return View(item);
        }

        [HttpPost]
        public ActionResult DeleteSave(MenuItem menuItem)
        {
            _menuItemProvider.Remove(menuItem);
            return RedirectToAction("Index", "MenuItem");
        }
    }
}
