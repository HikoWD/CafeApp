package by.ivan.CafeApp.domain.order.model

import by.ivan.CafeApp.ui.domain.table.model.Table

data class OrderParams(
    val orderDetails: OrderDetails,
    val table: Table
)