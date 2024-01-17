package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.ivan.CafeApp.ui.data.remote.model.CategoryRemoteModelList

@Entity
data class CategoryLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String
)

fun CategoryRemoteModelList.CategoryRemoteModel.toLocalModel() = CategoryLocalModel(
    id = id,
    title = title
)