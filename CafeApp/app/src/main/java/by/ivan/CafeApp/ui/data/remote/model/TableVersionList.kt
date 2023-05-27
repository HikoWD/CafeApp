package by.ivan.CafeApp.ui.data.remote.model

import javax.annotation.Nullable

data class TableVersionList(
    val items: List<TableVersion>
) {
    data class TableVersion(
        val id: Int,
        val tableName: String,
        val version: Int
    )
}