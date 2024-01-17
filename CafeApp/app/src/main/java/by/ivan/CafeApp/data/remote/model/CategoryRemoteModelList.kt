package by.ivan.CafeApp.data.remote.model

data class CategoryRemoteModelList(
    val items: List<CategoryRemoteModel>
) {
    data class CategoryRemoteModel(
        val id: Int,
        val title: String
    )
}