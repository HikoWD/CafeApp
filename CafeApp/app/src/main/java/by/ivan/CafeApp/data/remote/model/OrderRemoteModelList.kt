package by.ivan.CafeApp.data.remote.model

data class OrderRemoteModelList(
    val items: List<OrderRemoteModel>
) {
    data class OrderRemoteModel(
        val id: Int,
        val orderDetails: OrderDetailsRemoteModel,
        val orderDetailsId: Int,
        val orderState: Int,
        val table: TableRemoteModelList.TableRemoteModel,
        val tableId: Int,
        val timestamp: String
    ) {
//        data class OrderDetails(
//            val id: Int,
//            val menuItemsIdsText: String
//        )

//        data class Table(
//            val id: Int,
//            val state: Int,
//            val title: String
//        )
    }
}