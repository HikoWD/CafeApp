package by.ivan.CafeApp.domain.order.model

import by.ivan.CafeApp.domain.table.model.Table

data class OrderParams(
    val orderDetails: OrderDetails,
    val table: Table
)