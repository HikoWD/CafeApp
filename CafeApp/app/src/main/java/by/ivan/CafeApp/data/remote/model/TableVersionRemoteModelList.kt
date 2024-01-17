package by.ivan.CafeApp.data.remote.model

data class TableVersionRemoteModelList(
    val items: List<TableVersionRemoteModel>
) {
    data class TableVersionRemoteModel(
        val id: Int,
        val tableName: String,
        val version: Int
    )
}