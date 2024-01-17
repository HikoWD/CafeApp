using System.ComponentModel.DataAnnotations.Schema;
using System.Diagnostics;

namespace Cafe.Service.Models.Menu
{
    [DebuggerDisplay("{Title}")]
    public class MenuItem
    {
        public int Id { get; set; }
        public int CategoryId { get; set; }
        public string Image { get; set; }
        [NotMapped]
        public IFormFile ImageFile { get; set; }
        public Category? Category { get; set; }
        public string Title { get; set; }
        public string Description { get; set; }
        public double Price { get; set; }
        public double Weight { get; set; }

        public override bool Equals(object? obj)
        {
            var other = obj as MenuItem;
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
