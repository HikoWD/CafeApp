using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Table;
using Cafe.Service.Models.Versions;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Menu
{
    public class MenuItemProvider
    {
        private static string TableName = "MenuItem";


        private MenuItemRepository _menuItemRepository;
        private CategoryRepository _categoryRepository;
        private TableVersionRepository _tableVersionRepository;

        public MenuItemProvider(            
            MenuItemRepository menuItemRepository,
            CategoryRepository categoryRepository,
            TableVersionRepository tableVersionRepository)
        {            
            _menuItemRepository = menuItemRepository;
            _categoryRepository = categoryRepository;
            _tableVersionRepository = tableVersionRepository;
        }

        public CollectionResponse<MenuItem> Get()
        {
            return new CollectionResponse<MenuItem>(_menuItemRepository.Get().Include(x => x.Category));
        }

        public MenuItem Get(int id)
        {
            return _menuItemRepository.Get()
                .Include(x => x.Category)
                .FirstOrDefault(menuItem => menuItem.Id == id);
        }

        public MenuItem Add(MenuItem menuItem)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _menuItemRepository.Add(menuItem);
        }

        public MenuItem Edit(MenuItem menuItem)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _menuItemRepository.Edit(menuItem);
        }

        public MenuItem Remove(MenuItem menuItem)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _menuItemRepository.Remove(menuItem);
        }

        public int GetVersion()
        {
            return _tableVersionRepository.GetVersion(TableName);
        }
    }
}
