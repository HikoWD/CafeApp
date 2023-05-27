package by.ivan.CafeApp.ui.data.remote.model

data class MenuItems(
    val items: List<Item>
) {
    data class Item(
        val category: Category,
        val categoryId: Int,
        val description: String,
        val id: Int,
        val price: Double,
        val title: String,
        val weight: Double
    ) {
        data class Category(
            val id: Int,
            val title: String
        )
    }
}