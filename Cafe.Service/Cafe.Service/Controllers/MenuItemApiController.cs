using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Cafe.Service.Controllers
{
    [Route("api/menuitem")]
    [ApiController]
    public class MenuItemApiController : ControllerBase
    {
        private MenuItemProvider _menuItemProvider;

        public MenuItemApiController(MenuItemProvider menuItemProvider)
        {
            _menuItemProvider = menuItemProvider;
        }

        // GET: api/<MenuItemApiController>
        [HttpGet]
        public ActionResult<CollectionResponse<MenuItem>> Get()
        {
            return new JsonResult(_menuItemProvider.Get());
        }

        // GET api/<MenuItemApiController>/5
        [HttpGet("{id}")]
        public ActionResult<MenuItem> Get(int id)
        {
            var menuItem = _menuItemProvider.Get(id);
            if (menuItem == null)
            {
                return NotFound();
            }

            return new JsonResult(menuItem);
        }

        [HttpGet("version")]
        public ActionResult<int> GetVersion()
        {
            return Ok(_menuItemProvider.GetVersion());
        }

        // POST api/<MenuItemApiController>
        [HttpPost]
        public ActionResult<MenuItem> Post([FromBody] MenuItem menuItem)
        {
            if (menuItem == null)
            {
                return BadRequest();
            }

            return Ok(_menuItemProvider.Add(menuItem));
        }

        // PUT api/<MenuItemApiController>
        [HttpPut()]
        public ActionResult<MenuItem> Put([FromBody] MenuItem menuItem)
        {
            if (menuItem == null)
            {
                return BadRequest();
            }

            if (_menuItemProvider.Get(menuItem.Id) == null)
            {
                return NotFound();
            }

            return Ok(_menuItemProvider.Edit(menuItem));
        }

        // DELETE api/<MenuItemApiController>/5
        [HttpDelete("{id}")]
        public ActionResult<MenuItem> Delete(int id)
        {
            MenuItem menuItem = _menuItemProvider.Get(id);
            if (menuItem == null)
            {
                return NotFound();
            }

            return Ok(_menuItemProvider.Remove(menuItem));
        }
    }
}
