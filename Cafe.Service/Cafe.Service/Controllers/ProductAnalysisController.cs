using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Order;
using Cafe.Service.Models.ProductAnalysis;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Cafe.Service.Controllers
{
    public class ProductAnalysisController : Controller
    {
        private CategoryProvider _categoryProvider;
        private MenuItemProvider _menuItemProvider;
        private OrderProvider _orderProvider;

        public ProductAnalysisController(CategoryProvider categoryProvider,  
            MenuItemProvider menuItemProvider, 
            OrderProvider orderProvider)
        {
            _categoryProvider = categoryProvider;
            _menuItemProvider = menuItemProvider;
            _orderProvider = orderProvider;
        }

        public ActionResult Index()
        {
            Dictionary<Category, Dictionary<MenuItem, int>> menuItemsByCategory = 
                new Dictionary<Category, Dictionary<MenuItem, int>>();

            var categories = _categoryProvider.Get().Items.ToList();
            foreach (var category in categories)
            {
                menuItemsByCategory.Add(category, new Dictionary<MenuItem, int>());
            } 

            var orders = _orderProvider.Get();
            foreach(var order in orders.Items.ToList())
            {
                if(order.OrderDetails != null)
                {
                    foreach (var menuItemId in order.OrderDetails.MenuItemIds)
                    {
                        var menuItem = _menuItemProvider.Get(menuItemId);
                        if(menuItem != null)
                        {
                            var category = categories.FirstOrDefault(x => x.Id == menuItem.CategoryId);
                            if (category is not null)
                            {
                                if (!menuItemsByCategory[category].ContainsKey(menuItem))
                                {
                                    menuItemsByCategory[category].Add(menuItem, 1);
                                }
                                else
                                {
                                    menuItemsByCategory[category][menuItem] += 1;
                                }
                            }
                            
                        }
                    }
                }   
            }

            List<MostPopularProduct> products = new List<MostPopularProduct>();
            foreach (var menuItem in menuItemsByCategory)
            {
                var product = new MostPopularProduct();
                product.Category = menuItem.Key;
                if(menuItem.Value.First().Key != null)
                {
                    product.MenuItem = menuItem.Value.First().Key;
                }
                product.Count = menuItem.Value.First().Value;

                foreach (var item in menuItem.Value)
                {
                    if (product.Count < item.Value)
                    {
                        product.MenuItem = item.Key;
                        product.Count = item.Value;
                    }
                }

                products.Add(product);
            }

            return View(products);
        }

        public ActionResult Details()
        {
            Dictionary<Category, Dictionary<MenuItem, int>> menuItemsByCategory =
                new Dictionary<Category, Dictionary<MenuItem, int>>();

            var categories = _categoryProvider.Get().Items.ToList();
            foreach (var category in categories)
            {
                menuItemsByCategory.Add(category, new Dictionary<MenuItem, int>());
            }

            var orders = _orderProvider.Get();
            foreach (var order in orders.Items.ToList())
            {
                if (order.OrderDetails != null)
                {
                    foreach (var menuItemId in order.OrderDetails.MenuItemIds)
                    {
                        var menuItem = _menuItemProvider.Get(menuItemId);
                        if (menuItem != null)
                        {
                            var category = categories.FirstOrDefault(x => x.Id == menuItem.CategoryId);
                            if (category is not null)
                            {
                                if (!menuItemsByCategory[category].ContainsKey(menuItem))
                                {
                                    menuItemsByCategory[category].Add(menuItem, 1);
                                }
                                else
                                {
                                    menuItemsByCategory[category][menuItem] += 1;
                                }
                            }

                        }
                    }
                }
            }

            List<ProductDetails> products = new List<ProductDetails>();
            foreach (var menuItem in menuItemsByCategory)
            {
                var product = new ProductDetails();
                product.Category = menuItem.Key;
                product.MenuItems = new Dictionary<MenuItem, int>();
                foreach (var item in menuItem.Value)
                {
                    product.MenuItems.Add(item.Key, item.Value);                    
                }
                products.Add(product);
            }

            return View(products);
        }
    }
}
