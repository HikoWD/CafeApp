using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Shared;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Cafe.Service.Controllers
{
    [Route("api/category")]
    [ApiController]
    public class CategoryApiController : ControllerBase
    {
        private CategoryProvider _categoryProvider;

        public CategoryApiController(CategoryProvider categoryProvider)
        {
            _categoryProvider = categoryProvider;
        }

        // GET: api/<CategoryApiController>
        [HttpGet]
        public ActionResult<CollectionResponse<Category>> Get()
        {
            return new JsonResult(_categoryProvider.Get());
        }

        // GET api/<CategoryApiController>/5
        [HttpGet("{id}")]
        public ActionResult<Category> Get(int id)
        {
            var category = _categoryProvider.Get(id);
            if (category == null)
            {
                return NotFound();
            }

            return new JsonResult(category);
        }

        // POST api/<CategoryApiController>
        [HttpPost]
        public ActionResult<Category> Post([FromBody] Category category)
        {
            if (category == null)
            {
                return BadRequest();
            }

            return Ok(_categoryProvider.Add(category));
        }

        // PUT api/<CategoryApiController>
        [HttpPut()]
        public ActionResult<Category> Put([FromBody] Category category)
        {
            if (category == null)
            {
                return BadRequest();
            }

            if (_categoryProvider.Get(category.Id) == null)
            {
                return NotFound();
            }

            return Ok(_categoryProvider.Edit(category));
        }

        // DELETE api/<CategoryApiController>/5
        [HttpDelete("{id}")]
        public ActionResult<Category> Delete(int id)
        {
            Category category = _categoryProvider.Get(id);
            if (category == null)
            {
                return NotFound();
            }

            return Ok(_categoryProvider.Remove(category));
        }
    }
}
