package by.ivan.CafeApp.ui.data.repo

import android.content.Context
import by.ivan.CafeApp.ui.data.datasource.OrderLocalDatasource
import by.ivan.CafeApp.ui.data.datasource.OrderRemoteDatasource
import by.ivan.CafeApp.ui.data.local.entity.OrderDetailsLocalModel
import by.ivan.CafeApp.ui.data.local.entity.OrderLocalModel
import by.ivan.CafeApp.ui.data.models.Order
import by.ivan.CafeApp.ui.data.models.OrderDetails
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderLocalDatasource: OrderLocalDatasource,
    private val orderRemoteDatasource: OrderRemoteDatasource,
    private val connectionStateRepository: ConnectionStateRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun getAll() {

    }

    suspend fun insert(order: Order) {

    }

    suspend fun getOrdersByTable(tableId: Int): List<Order> {
        if (connectionStateRepository.checkForInternet(context)) {
            val ordersRemote = orderRemoteDatasource.getOrdersByTable(tableId = tableId).items
            orderLocalDatasource.removeAll()
            orderLocalDatasource.saveOrders(ordersRemote.map {
                OrderLocalModel(
                    id = it.id,
                    orderDetails = "${it.orderDetails.id}-${it.orderDetails.menuItemsIdsText}",
                    tableId = it.tableId,
                    timestamp = it.timestamp,
                )
            })
            return orderLocalDatasource.getAll().map {
                Order(
                    id = it.id,

                    orderDetails = OrderDetails(
                        id = it.orderDetails.split("-")[0].toInt(),
                        menuItemsIdsText = it.orderDetails.split("-")[1],
                    ),
                    tableId = it.tableId,
                    timestamp = it.timestamp
                )
            }
        }
        else{
            return orderLocalDatasource.getAll().map {
                Order(
                    id = it.id,
                    orderDetails = OrderDetails(
                        id = it.orderDetails.split("-")[0].toInt(),
                        menuItemsIdsText = it.orderDetails.split("-")[1],
                    ),
                    tableId = it.tableId,
                    timestamp = it.timestamp
                )
            }
        }
    }

    suspend fun getOrderDetailsById(orderDetailsId: Int): OrderDetails {
        with(orderRemoteDatasource.getOrderDetailsById(orderDetailsId = orderDetailsId)) {
            return OrderDetails(
                id = this.id,
                menuItemsIdsText = this.menuItemsIdsText
            )
        }
    }
}