using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Operator;
using Cafe.Service.Models.Order;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Cafe.Service.Controllers
{
    [Route("api/order")]
    [ApiController]
    public class OrderApiController : ControllerBase
    {
        private OrderProvider _orderProvider;
        private OrderDetailsRepository _orderDetailsRepository;
        private MenuItemProvider _menuItemProvider;

        public OrderApiController(
            OrderProvider orderProvider, 
            OrderDetailsRepository orderDetailsRepository,
            MenuItemProvider menuItemProvider)
        {
            _orderProvider = orderProvider;
            _orderDetailsRepository = orderDetailsRepository;
            _menuItemProvider = menuItemProvider;
        }
        // GET: api/<OrderApiController>
        [HttpGet]
        public ActionResult<CollectionResponse<Order>> Get()
        {
            return new JsonResult(_orderProvider.Get());
        }

        // GET api/<OrderApiController>/5
        [HttpGet("{id}")]
        public ActionResult<Order> Get(int id)
        {
            var order = _orderProvider.Get(id);
            if (order == null)
            {
                return NotFound();
            }

            return new JsonResult(order);
        }

        // GET api/<OrderApiController>/tables/5
        [HttpGet("table/{tableId}")]
        public ActionResult<Order> GetByTableId(int tableId)
        {
            var order = _orderProvider.GetByTableId(tableId);
            if (order == null)
            {
                return NotFound();
            }

            return new JsonResult(order);
        }

        // GET api/<OrderApiController>/orderDetails/5
        //[HttpGet("orderDetails/{orderDetailsId}")]
        //public ActionResult<OrderDetails> GetByOrderDetailsId(int orderDetailsId)
        //{
        //    var orderDetails = _orderDetailsRepository.Get().FirstOrDefault(x => x.Id == orderDetailsId);
        //    if (orderDetails == null)
        //    {
        //        return NotFound();
        //    }

        //    return new JsonResult(orderDetails);
        //}

        // POST api/<OrderApiController>
        [HttpPost]
        public ActionResult<Order> Post([FromBody] OrderParams orderParams)
        {
            if (orderParams == null)
            {
                return BadRequest();
            }
            
            if(orderParams.Table.Id == -1)
            {
                return NotFound();
            }

            var orderDetails = orderParams.OrderDetails;
            foreach (var menuItemId in orderDetails.MenuItemIds)
            {
                if (_menuItemProvider.Get(menuItemId) == null)
                {
                    return NotFound();
                }
            }

            return new JsonResult(_orderProvider.Add(orderDetails, orderParams.Table));
        }

        //// PUT api/<OrderApiController>/5
        //[HttpPut("{id}")]
        //public void Put(int id, [FromBody] string value)
        //{
        //}

        //// DELETE api/<OrderApiController>/5
        //[HttpDelete("{id}")]
        //public void Delete(int id)
        //{
        //}
    }
}
