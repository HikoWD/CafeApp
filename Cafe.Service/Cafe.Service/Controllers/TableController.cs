using Cafe.Service.Models.Table;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.Mvc.Rendering;

namespace Cafe.Service.Controllers
{
    [Authorize]
    public class TableController : Controller
    {
        private TableProvider _tableProvider;

        public TableController(TableProvider tableProvider)
        {
            _tableProvider = tableProvider;
        }
        // GET: TableController
        public ActionResult Index()
        {
            return View(_tableProvider.Get());
        }

        public ActionResult Add()
        {
            SelectList states = new SelectList(Enum.GetValues<State>());
            ViewBag.States = states;
            return View();
        }

        [HttpPost]
        public ActionResult AddSave(Table table)
        {
            _tableProvider.Add(table);
            return RedirectToAction("Index", "Table");
        }

        public ActionResult Update(Table table)
        {
            SelectList states = new SelectList(Enum.GetValues<State>());
            ViewBag.States = states;
            return View(table);
        }

        [HttpPost]
        public ActionResult UpdateSave(Table table)
        {
            _tableProvider.Edit(table);
            return RedirectToAction("Index", "Table");
        }

        public ActionResult Delete(Table table)
        {
            return View(table);
        }

        [HttpPost]
        public ActionResult DeleteSave(Table table)
        {
            _tableProvider.Remove(table);
            return RedirectToAction("Index", "Table");
        }
    }
}
