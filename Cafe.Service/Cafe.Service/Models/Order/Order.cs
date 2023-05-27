namespace Cafe.Service.Models.Order
{
    public class Order
    {
        public int Id { get; set; }
        public int OrderDetailsId { get; set; }
        public OrderDetails? OrderDetails { get; set; }
        public int? OperatorId { get; set; }
        public Operator.Operator? Operator { get; set; }
        public int TableId { get; set; }
        public Table.Table? Table { get; set; }
        public DateTime Timestamp { get; set; }
        public OrderState OrderState { get; set; } = OrderState.Pending;
    }
}
