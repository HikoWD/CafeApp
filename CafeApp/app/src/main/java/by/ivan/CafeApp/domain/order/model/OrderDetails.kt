package by.ivan.CafeApp.domain.order.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class OrderDetails(
    val id: Int? = null,
    var menuItemsIdsText: String = ""
) : Parcelable

fun String.toDomain() = OrderDetails(
    id = this.split("-")[0].toInt(),
    menuItemsIdsText = this.split("-")[1]
)