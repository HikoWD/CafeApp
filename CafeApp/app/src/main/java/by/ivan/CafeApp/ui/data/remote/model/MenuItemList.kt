package by.ivan.CafeApp.ui.data.remote.model

data class MenuItemList(
    val items: List<Item>
) {
    data class Item(
        val id: Int,
        val image : String,
        val categoryId: Int,
        val description: String,
        val price: Double,
        val title: String,
        val weight: Double
    )
}