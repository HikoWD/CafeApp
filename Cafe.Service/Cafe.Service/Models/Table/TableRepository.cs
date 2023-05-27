using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Table
{
    public class TableRepository
    {
        private ApplicationContext _context;

        public TableRepository(ApplicationContext context)
        {
            _context = context;
        }

        public DbSet<Table> Get()
        {
            return _context.Tables;
        }

        public Table Add(Table table)
        {
            _context.Tables.Add(table);
            _context.SaveChanges();
            return table;
        }

        public Table Edit(Table table)
        {
            var item = _context.Tables.FirstOrDefault(c => c.Id == table.Id);
            if (item == null)
            {
                throw new ArgumentOutOfRangeException();
            }
            item.Title = table.Title;
            item.State = table.State;
            _context.SaveChanges();
            return item;
        }

        public Table Remove(Table table)
        {
            _context.Tables.Remove(table);
            _context.SaveChanges();
            return table;
        }
    }
}
