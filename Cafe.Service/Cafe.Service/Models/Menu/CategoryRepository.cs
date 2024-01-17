using Cafe.Service.Models.Shared;
using Microsoft.Data.SqlClient;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Menu
{
    public class CategoryRepository
    {
        private ApplicationContext _context;

        public CategoryRepository(ApplicationContext context)
        {
            _context = context;
        }
        
        public List<Category> Get()
        {
            var categories = _context.Categories
        .FromSqlRaw("EXECUTE dbo.GetCategories")
        .AsEnumerable()
        .ToList();
            return categories;
        //    return _context.Categories;
        }

        //public DbSet<Category> Get()
        //{
        //        return _context.Categories;
        //}

        public Category Get(int id)
        {
            var category = _context.Categories
        .FromSqlRaw($"EXECUTE dbo.GetCategoryById {id}")
        .AsEnumerable()
        .FirstOrDefault();
            return category;
          //  return _context.Categories.FirstOrDefault(category => category.Id == id);
        }

        public Category Add(Category category)
        {
            var addedCategory = _context.Categories
        .FromSqlRaw($"EXECUTE dbo.AddCategory {category.Title}")
        .AsEnumerable()
        .FirstOrDefault();
            return addedCategory;

            //_context.Categories.Add(category);
            //_context.SaveChanges();
            //return category;
        }

        public Category Edit(Category category)
        {
            _context.Database.ExecuteSql($"EXECUTE dbo.EditCategory {category.Id}, {category.Title}");

            return category;
            //var item = _context.Categories.FirstOrDefault(c => c.Id == category.Id);
            //if (item == null)
            //{
            //    throw new ArgumentOutOfRangeException();
            //}
            //item.Title = category.Title;
            //_context.SaveChanges();
            //return item;
        }

        public Category Remove(Category category)
        {
            _context.Database.ExecuteSql($"EXECUTE dbo.RemoveCategory {category.Id}");
            _context.SaveChanges();
            return category;
        }
    }
}
