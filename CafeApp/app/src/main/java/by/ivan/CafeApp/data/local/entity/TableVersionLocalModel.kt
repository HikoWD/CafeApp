package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.ivan.CafeApp.data.remote.model.TableVersionRemoteModelList

@Entity
data class TableVersionLocalModel (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val tableName: String,
    var version: Int
)

fun TableVersionRemoteModelList.TableVersionRemoteModel.toLocalModel() = TableVersionLocalModel(
    id = id,
    tableName = tableName,
    version = version
)