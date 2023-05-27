using Cafe.Service.Models.Menu;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Cafe.Service.Controllers
{
    [Authorize]
    public class CategoryController : Controller
    {
        CategoryProvider _categoryProvider;

        public CategoryController(CategoryProvider categoryProvider)
        {
            _categoryProvider = categoryProvider;
        }

        public ActionResult Index()
        {
            return View(_categoryProvider.Get());
        }

        public ActionResult Add()
        {           
            return View();
        }

        [HttpPost]
        public ActionResult AddSave(Category category)
        {
            _categoryProvider.Add(category);
            return RedirectToAction("Index", "Category");
        }

        public ActionResult Update(Category category)
        {
            return View(category);
        }

        [HttpPost]
        public ActionResult UpdateSave(Category category)
        {
            _categoryProvider.Edit(category);
            return RedirectToAction("Index", "Category");
        }

        public ActionResult Delete(Category category)
        {
            return View(category);
        }

        [HttpPost]
        public ActionResult DeleteSave(Category category)
        {
            _categoryProvider.Remove(category);
            return RedirectToAction("Index", "Category");
        }
    }
}
