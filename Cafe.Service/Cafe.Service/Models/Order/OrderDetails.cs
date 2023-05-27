using Cafe.Service.Models.Menu;
using System.ComponentModel;
using System.ComponentModel.DataAnnotations.Schema;

namespace Cafe.Service.Models.Order
{
    public class OrderDetails
    {
        public int Id { get; set; }
        [EditorBrowsable(EditorBrowsableState.Never)]
        public string MenuItemsIdsText { get; set; }
        [NotMapped]
        public List<int> MenuItemIds
        {
            get
            {
                if (!string.IsNullOrEmpty(MenuItemsIdsText))
                {
                    return MenuItemsIdsText.Split(';').Select(x => int.Parse(x)).ToList();
                }
                else
                {
                    return new List<int>();
                }
            }
            set
            {
                if (value != null && value.Count > 0)
                {
                    MenuItemsIdsText = string.Join(';', value);
                }
                else
                {
                    MenuItemsIdsText = "";
                }
            }
        }
    }
}
