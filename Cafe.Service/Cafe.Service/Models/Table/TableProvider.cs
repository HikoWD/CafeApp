using Cafe.Service.Models.Shared;
using Cafe.Service.Models.Versions;
using Microsoft.EntityFrameworkCore.Metadata.Internal;

namespace Cafe.Service.Models.Table
{
    public class TableProvider
    {
        public static string TableName = "Table";

        private TableRepository _tableRepository;
        private TableVersionRepository _tableVersionRepository;

        public TableProvider(TableRepository tableRepository, TableVersionRepository tableVersionRepository)
        {
            _tableRepository = tableRepository;
            _tableVersionRepository = tableVersionRepository;
        }

        public CollectionResponse<Table> Get()
        {
            return new CollectionResponse<Table>(_tableRepository.Get());
        }

        public Table Get(int id)
        {
            return _tableRepository.Get().FirstOrDefault(table => table.Id == id);
        }

        public Table Add(Table table)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _tableRepository.Add(table);
        }

        public Table Edit(Table table)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _tableRepository.Edit(table);
        }

        public Table Remove(Table table)
        {
            _tableVersionRepository.IncrementVersion(TableName);
            return _tableRepository.Remove(table);
        }

        public int GetVersion()
        {
            return _tableVersionRepository.GetVersion(TableName);
        }
    }
}
