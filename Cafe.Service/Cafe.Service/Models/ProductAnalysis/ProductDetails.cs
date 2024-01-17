using Cafe.Service.Models.Menu;

namespace Cafe.Service.Models.ProductAnalysis
{
    public class ProductDetails
    {
        public Category Category { get; set; }
        public Dictionary<MenuItem, int> MenuItems { get; set; }
    }
}
