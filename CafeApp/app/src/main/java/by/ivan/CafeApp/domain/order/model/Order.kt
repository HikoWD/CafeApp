package by.ivan.CafeApp.domain.order.model

import android.os.Parcelable
import by.ivan.CafeApp.ui.data.local.entity.OrderLocalModel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: Int = 0,
    val orderDetails: OrderDetails = OrderDetails(),
    val tableId: Int = 0,
    val timestamp: String = ""
) : Parcelable

fun OrderLocalModel.toDomain() = Order(
    id = id,
    orderDetails = this.orderDetails.toDomain(),
    tableId = tableId,
    timestamp = timestamp
)