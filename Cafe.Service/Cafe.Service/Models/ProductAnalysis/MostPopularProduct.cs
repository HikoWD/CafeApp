using Cafe.Service.Models.Menu;

namespace Cafe.Service.Models.ProductAnalysis
{
    public class MostPopularProduct
    {
        public Category Category { get; set; }
        public MenuItem MenuItem { get; set; }
        public int Count { get; set; }
    }
}
