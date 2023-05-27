using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Cafe.Service.Controllers
{
    [Route("api/table")]
    [ApiController]
    public class TableApiController : ControllerBase
    {
        private TableProvider _tableProvider;

        public TableApiController(TableProvider tableProvider)
        {
            _tableProvider = tableProvider;
        }

        // GET: api/<TableApiController>
        [HttpGet]
        public ActionResult<CollectionResponse<Table>> Get()
        {
            return new JsonResult(_tableProvider.Get());
        }

        // GET api/<TableApiController>/5
        [HttpGet("{id}")]
        public ActionResult<Table> Get(int id)
        {
            var table = _tableProvider.Get(id);
            if (table == null)
            {
                return NotFound();
            }

            return new JsonResult(table);
        }

        [HttpGet("version")]
        public ActionResult<int> GetVersion()
        {
            return Ok(_tableProvider.GetVersion());
        }

        // POST api/<TableApiController>
        [HttpPost]
        public ActionResult<Table> Post([FromBody] Table table)
        {
            if (table == null)
            {
                return BadRequest();
            }

            return Ok(_tableProvider.Add(table));
        }

        // PUT api/<TableApiController>
        [HttpPut()]
        public ActionResult<Table> Put([FromBody] Table table)
        {
            if (table == null)
            {
                return BadRequest();
            }

            if (_tableProvider.Get(table.Id) == null)
            {
                return NotFound();
            }

            return Ok(_tableProvider.Edit(table));
        }

        // DELETE api/<TableApiController>/5
        [HttpDelete("{id}")]
        public ActionResult<Table> Delete(int id)
        {
            Table table = _tableProvider.Get(id);
            if (table == null)
            {
                return NotFound();
            }

            return Ok(_tableProvider.Remove(table));
        }
    }
}
