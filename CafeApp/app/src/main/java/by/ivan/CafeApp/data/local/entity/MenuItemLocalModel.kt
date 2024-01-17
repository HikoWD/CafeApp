package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import by.ivan.CafeApp.data.remote.model.MenuItemRemoteModelList

@Entity(
    foreignKeys = [ForeignKey(
        entity = CategoryLocalModel::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onUpdate = ForeignKey.CASCADE,
        //onDelete = ForeignKey.CASCADE //todo
    )]
)
data class MenuItemLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val categoryId: Int,
    val image: String,
    val title: String,
    val description: String,
    val price: Double,
    val weight: Double
)

fun MenuItemRemoteModelList.MenuItemRemoteModel.toLocalModel() = MenuItemLocalModel(
    id = id,
    categoryId = categoryId,
    image = image,
    title = title,
    description = description,
    price = price,
    weight = weight
)