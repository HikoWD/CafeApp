using Cafe.Service.Models.Shared;
using Microsoft.EntityFrameworkCore;

namespace Cafe.Service.Models.Versions
{
    public class TableVersionRepository
    {
        private ApplicationContext _context;

        public TableVersionRepository(ApplicationContext context)
        {
            _context = context;
        }

        public CollectionResponse<TableVersion> Get()
        {
            return new CollectionResponse<TableVersion>(_context.TableVersions);
        }

        public int GetVersion(string tableName)
        {
            var version = _context.TableVersions.FirstOrDefault(x => x.TableName == tableName);
            if (version != null)
            {
                return version.Version;
            }
            return 0;
        }

        public void IncrementVersion(string tableName)
        {
            var version = _context.TableVersions.FirstOrDefault(x => x.TableName == tableName);
            if (version != null)
            {
                version.Version++;
                _context.SaveChanges();
            }
            else
            {
                version = new TableVersion
                {
                    TableName = tableName,
                    Version = 1
                };
                _context.TableVersions.Add(version);
                _context.SaveChanges();
            }
        }
    }
}
