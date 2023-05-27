using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Order
{
    public class OrderRepository
    {
        private ApplicationContext _context;

        public OrderRepository(ApplicationContext context)
        {
            _context = context;
        }

        public DbSet<Order> Get()
        {
            return _context.Orders;
        }

        public Order Add(Order order)
        {
            _context.Orders.Add(order);
            _context.SaveChanges();
            return order;
        }

        public Order Edit(Order order)
        {
            var item = _context.Orders.FirstOrDefault(c => c.Id == order.Id);
            if (item == null)
            {
                throw new ArgumentOutOfRangeException();
            }
            item.OrderState = order.OrderState;
            item.Timestamp = order.Timestamp;
            _context.SaveChanges();
            return item;
        }

        //public Order Remove(Order order)
        //{
        //    _context.Orders.Remove(order);
        //    _context.SaveChanges();
        //    return order;
        //}
    }
}
