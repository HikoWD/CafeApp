namespace Cafe.Service.Models.Shared
{
    public class CollectionResponse<T>
    {
        public IEnumerable<T> Items { get; }

        public CollectionResponse(IEnumerable<T> items) => Items = items;
    }
}
