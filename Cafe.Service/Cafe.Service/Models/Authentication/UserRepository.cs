using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Authentication
{
    public class UserRepository
    {
        ApplicationContext _context;

        public const string AdminRole = "Admin";
        public const string OperatorRole = "Operator";

        public UserRepository(ApplicationContext context)
        {
            _context = context;
        }

        public DbSet<Person> Get()
        {
            return _context.Persons;
        }
    }
}
