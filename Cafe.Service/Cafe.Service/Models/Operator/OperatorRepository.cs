using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Operator
{
    public class OperatorRepository
    {
        private ApplicationContext _context;

        public OperatorRepository(ApplicationContext context)
        {
            _context = context;
        }

        public DbSet<Operator> Get()
        {
            return _context.Operators;
        }

        public Operator Add(Operator @operator)
        {
            _context.Operators.Add(@operator);
            _context.SaveChanges();
            return @operator;
        }

        public Operator Edit(Operator @operator)
        {
            var item = _context.Operators.FirstOrDefault(c => c.Id == @operator.Id);
            if (item == null)
            {
                throw new ArgumentOutOfRangeException();
            }
            item.FullName = @operator.FullName;
            item.BirthDate = @operator.BirthDate;
            item.PhoneNumber = @operator.PhoneNumber;
            _context.SaveChanges();
            return item;
        }

        public Operator Remove(Operator @operator)
        {
            _context.Operators.Remove(@operator);
            _context.SaveChanges();
            return @operator;
        }
    }
}
