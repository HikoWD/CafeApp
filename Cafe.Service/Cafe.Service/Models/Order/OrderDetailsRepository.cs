using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Order
{
    public class OrderDetailsRepository
    {
        private ApplicationContext _context;

        public OrderDetailsRepository(ApplicationContext context)
        {
            _context = context;
        }

        public DbSet<OrderDetails> Get()
        {
            return _context.OrderDetails;
        }

        public OrderDetails Add(OrderDetails orderDetails)
        {
            _context.OrderDetails.Add(orderDetails);
            _context.SaveChanges();
            return orderDetails;
        }

        public OrderDetails Remove(OrderDetails orderDetails)
        {
            _context.OrderDetails.Remove(orderDetails);
            _context.SaveChanges();
            return orderDetails;
        }
    }
}
