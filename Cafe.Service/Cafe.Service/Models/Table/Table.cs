namespace Cafe.Service.Models.Table
{
    public class Table
    {
        public int Id { get; set; }
        public string Title { get; set; }
        public State State { get; set; } = State.NotOccupied;
    }
}