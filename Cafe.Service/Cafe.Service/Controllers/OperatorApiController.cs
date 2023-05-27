using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Operator;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Cafe.Service.Controllers
{
    [Route("api/operator")]
    [ApiController]
    public class OperatorApiController : ControllerBase
    {
        private OperatorProvider _operatorProvider;

        public OperatorApiController(OperatorProvider operatorProvider)
        {
            _operatorProvider = operatorProvider;
        }

        // GET: api/<OperatorApiController>
        [HttpGet]
        public ActionResult<CollectionResponse<Operator>> Get()
        {
            return new JsonResult(_operatorProvider.Get());
        }

        // GET api/<OperatorApiController>/5
        [HttpGet("{id}")]
        public ActionResult<Operator> Get(int id)
        {
            var @operator = _operatorProvider.Get(id);
            if (@operator == null)
            {
                return NotFound();
            }

            return new JsonResult(@operator);
        }

        // POST api/<OperatorApiController>
        [HttpPost]
        public ActionResult<Operator> Post([FromBody] Operator @operator)
        {
            if (@operator == null)
            {
                return BadRequest();
            }

            return Ok(_operatorProvider.Add(@operator));
        }

        // PUT api/<OperatorApiController>
        [HttpPut()]
        public ActionResult<Operator> Put([FromBody] Operator @operator)
        {
            if (@operator == null)
            {
                return BadRequest();
            }

            if (_operatorProvider.Get(@operator.Id) == null)
            {
                return NotFound();
            }

            return Ok(_operatorProvider.Edit(@operator));
        }

        // DELETE api/<OperatorApiController>/5
        [HttpDelete("{id}")]
        public ActionResult<Operator> Delete(int id)
        {
            Operator @operator = _operatorProvider.Get(id);
            if (@operator == null)
            {
                return NotFound();
            }

            return Ok(_operatorProvider.Remove(@operator));
        }
    }
}
