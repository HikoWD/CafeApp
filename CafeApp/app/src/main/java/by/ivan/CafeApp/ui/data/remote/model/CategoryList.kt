package by.ivan.CafeApp.ui.data.remote.model

data class CategoryList(
    val items: List<Category>
) {
    data class Category(
        val id: Int,
        val title: String
    )
}