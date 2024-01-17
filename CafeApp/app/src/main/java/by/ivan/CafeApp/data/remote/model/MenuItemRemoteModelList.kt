package by.ivan.CafeApp.data.remote.model

data class MenuItemRemoteModelList(
    val items: List<MenuItemRemoteModel>
) {
    data class MenuItemRemoteModel(
        val id: Int,
        val image : String,
        val categoryId: Int,
        val description: String,
        val price: Double,
        val title: String,
        val weight: Double
    )
}