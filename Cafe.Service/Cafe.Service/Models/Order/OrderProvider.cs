using Cafe.Service.Models.Operator;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Order
{
    public class OrderProvider
    {
        private OrderRepository _orderRepository;
        private OrderDetailsRepository _orderDetailsRepository;
        private OperatorRepository _operatorRepository;
        private TableRepository _tableRepository;

        public OrderProvider(
            OrderRepository orderRepository,
            OrderDetailsRepository orderDetailsRepository,
            OperatorRepository operatorRepository,
            TableRepository tableRepository)
        {
            _orderRepository = orderRepository;
            _orderDetailsRepository = orderDetailsRepository;
            _operatorRepository = operatorRepository;
            _tableRepository = tableRepository;
        }

        public CollectionResponse<Order> Get()
        {
            return new CollectionResponse<Order>(_orderRepository.Get()
                .Include(order => order.OrderDetails)
                .Include(order => order.Operator)
                .Include(order => order.Table));
        }


        public Order Get(int id)
        {
            return _orderRepository.Get()
                .Include(order => order.OrderDetails)
                .Include(order => order.Operator)
                .Include(order => order.Table)
                .FirstOrDefault(order => order.Id == id);
        }

        public CollectionResponse<Order> GetByTableId(int tableId)
        {
            return new CollectionResponse<Order>(_orderRepository.Get()
                .Where(x => x.TableId == tableId)
                .Include(order => order.OrderDetails)
                .Include(order => order.Operator)
                .Include(order => order.Table));
        }

        public Order Add(OrderDetails orderDetails, Table.Table table)
        {
            _orderDetailsRepository.Add(orderDetails);
            //_operatorRepository.Add(@operator);
            //_tableRepository.Add(table);

            var order = new Order()
            {
                OrderDetailsId = orderDetails.Id,
                TableId = table.Id,
                Timestamp = DateTime.UtcNow
            };

            return _orderRepository.Add(order);
        }

        public Order Edit(Order order)
        {
            return _orderRepository.Edit(order);
        }
    }
}
