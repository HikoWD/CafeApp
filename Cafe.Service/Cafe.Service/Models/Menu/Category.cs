using System.Diagnostics;

namespace Cafe.Service.Models.Menu
{
    [DebuggerDisplay("{Title}")]
    public class Category
    {
        public int Id { get; set; }
        public string Title { get; set; }

        public override bool Equals(object? obj)
        {
            var other = obj as Category;
            if (other is null)
            {
                return false;
            }
            return Id == other.Id;
        }

        public override int GetHashCode()
        {
            return HashCode.Combine(Id);
        }
    }
}
