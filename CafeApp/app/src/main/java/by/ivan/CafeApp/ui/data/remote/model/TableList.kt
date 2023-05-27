package by.ivan.CafeApp.ui.data.remote.model

data class TableList(
    val items: List<Item>
) {
    data class Item(
        val id: Int,
        val state: Int,
        val title: String
    )
}