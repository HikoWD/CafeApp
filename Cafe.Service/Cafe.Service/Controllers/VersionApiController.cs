using Cafe.Service.Models.Menu;
using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Cafe.Service.Models.Versions;
using Microsoft.AspNetCore.Mvc;

// For more information on enabling Web API for empty projects, visit https://go.microsoft.com/fwlink/?LinkID=397860

namespace Cafe.Service.Controllers
{
    [Route("api/version")]
    [ApiController]
    public class VersionApiController : ControllerBase
    {
        private TableVersionRepository _tableVersionRepository;

        public VersionApiController(TableVersionRepository tableVersionRepository)
        {
            _tableVersionRepository = tableVersionRepository;
        }

        // GET: api/<VersionApiController>
        [HttpGet]
        public ActionResult<CollectionResponse<TableVersion>>Get()
        {
            return new JsonResult(_tableVersionRepository.Get());
        }
    }
}
