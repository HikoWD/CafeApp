using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Order;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Cafe.Service.Controllers
{
    [Authorize]
    public class OrderController : Controller
    {
        private OrderProvider _orderProvider;
        private MenuItemProvider _menuItemProvider;
        private OrderDetailsRepository _orderDetailsRepository;

        public OrderController(OrderProvider orderProvider, 
            MenuItemProvider menuItemProvider,
            OrderDetailsRepository orderDetailsRepository) 
        {
            _orderProvider = orderProvider;
            _menuItemProvider = menuItemProvider;
            _orderDetailsRepository = orderDetailsRepository;

        }
        public ActionResult Index()
        {
            //ViewBag.MenuItems = _menuItemProvider.Get();
            return View(_orderProvider.Get());
        }

        public ActionResult Add()
        {
            return View();
        }

        [HttpPost]
        public ActionResult AddSave(Order order)
        {
            //_orderProvider.Add(order);
            return RedirectToAction("Index", "Order");
        }

        public ActionResult Details(Order order)
        {
            var orderMenuItems = new List<MenuItem>();
            var menuItems = _menuItemProvider.Get().Items;
            // var orderDetails = _orderDetailsRepository.Get();
            var o = _orderProvider.Get(order.Id);
            foreach (var id in o.OrderDetails.MenuItemIds)
            {
                orderMenuItems.Add(menuItems.First(x => x.Id == id));
            }
            ViewBag.MenuItems = orderMenuItems;
            return View(order);
        }

        public ActionResult Success(Order order)
        {
            order.OrderState = OrderState.Success;
            _orderProvider.Edit(order);
            return RedirectToAction("Index", "Order");
        }

        public ActionResult Cancel(Order order)
        {
            order.OrderState = OrderState.Cancel;
            _orderProvider.Edit(order);
            return RedirectToAction("Index", "Order");
        }
    }
}
