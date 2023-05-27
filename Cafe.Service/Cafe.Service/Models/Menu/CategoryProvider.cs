using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Versions;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Menu
{
    public class CategoryProvider
    {
        private static string TableName = "Category";

        private CategoryRepository _categoryRepository;
        private MenuItemProvider _menuItemProvider;
        private TableVersionRepository _tableVersionRepository;

        public CategoryProvider(
            CategoryRepository categoryRepository,
            MenuItemProvider menuItemProvider,
            TableVersionRepository tableVersionRepository)
        {
            _categoryRepository = categoryRepository;
            _menuItemProvider = menuItemProvider;
            _tableVersionRepository = tableVersionRepository;
        }

        public CollectionResponse<Category> Get()
        {
            return new CollectionResponse<Category>(_categoryRepository.Get());
        }

        public Category Get(int id)
        {
            return _categoryRepository.Get().FirstOrDefault(x => x.Id == id);
        }

        public Category Add(Category category)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _categoryRepository.Add(category);
        }

        public Category Edit(Category category)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _categoryRepository.Edit(category);
        }

        public Category Remove(Category category)
        {
            var items = _menuItemProvider.Get().Items.Where(x => x.Category.Id == category.Id).ToList();
            foreach (var item in items)
            {
                _menuItemProvider.Remove(item);
            }
            _tableVersionRepository.IncrementVersion(TableName);
            return _categoryRepository.Remove(category);
        }
    }
}
