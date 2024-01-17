package by.ivan.CafeApp.domain.menu.model

import android.os.Parcelable
import by.ivan.CafeApp.ui.data.local.entity.MenuItemLocalModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class MenuItem(
    val id: Int = -1,
    val categoryId: Int = -1,
    val image: String = "",
    val description: String = "",
    val price: Double = 0.0,
    val title: String = "",
    val weight: Double = 0.0
) : Parcelable

fun MenuItemLocalModel.toDomain() = MenuItem(
    id = id,
    categoryId = categoryId,
    image = image,
    description = description,
    price = price,
    title = title,
    weight = weight
)