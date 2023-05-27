using System.ComponentModel.DataAnnotations.Schema;

namespace Cafe.Service.Models.Menu
{
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
    }
}
