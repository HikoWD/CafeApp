using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Menu
{
    public class MenuItemRepository
    {
        private ApplicationContext _context;

        public MenuItemRepository(ApplicationContext context)
        {
            _context = context;
    }

        public DbSet<MenuItem> Get()
        {
            return _context.MenuItems;
        }

        public MenuItem Add(MenuItem menuItem)
        {
            _context.MenuItems.Add(menuItem);
            _context.SaveChanges();
            return menuItem;
        }

        public MenuItem Edit(MenuItem menuItem)
        {
            var item = _context.MenuItems.FirstOrDefault(c => c.Id == menuItem.Id);
            if (item == null)
            {
                throw new ArgumentOutOfRangeException();
            }           
            item.CategoryId = menuItem.CategoryId;
            item.Title = menuItem.Title;
            item.Description = menuItem.Description;
            item.Price = menuItem.Price;
            item.Weight = menuItem.Weight;
            _context.SaveChanges();
            return item;
        }

        public MenuItem Remove(MenuItem menuItem)
        {
            _context.MenuItems.Remove(menuItem);
            _context.SaveChanges();
            return menuItem;
        }
    }
}
