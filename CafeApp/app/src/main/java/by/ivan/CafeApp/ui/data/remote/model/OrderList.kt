package by.ivan.CafeApp.ui.data.remote.model

data class OrderList(
    val items: List<Item>
) {
    data class Item(
        val id: Int,
        val orderDetails: OrderDetails,
        val orderDetailsId: Int,
        val orderState: Int,
        val table: Table,
        val tableId: Int,
        val timestamp: String
    ) {
        data class OrderDetails(
            val id: Int,
            val menuItemsIdsText: String
        )

        data class Table(
            val id: Int,
            val state: Int,
            val title: String
        )
    }
}