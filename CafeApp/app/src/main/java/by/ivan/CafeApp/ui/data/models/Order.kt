package by.ivan.CafeApp.ui.data.models

import java.util.*

data class Order(
    val id: Int,
    val orderDetails: OrderDetails,
    val tableId: Int,
    val timestamp: String
)
