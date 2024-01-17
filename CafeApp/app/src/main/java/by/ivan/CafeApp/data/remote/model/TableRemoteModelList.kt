package by.ivan.CafeApp.data.remote.model

data class TableRemoteModelList(
    val items: List<TableRemoteModel>
) {
    data class TableRemoteModel(
        val id: Int,
        val state: Int,
        val title: String
    )
}