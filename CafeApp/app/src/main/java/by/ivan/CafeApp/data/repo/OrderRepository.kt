package by.ivan.CafeApp.data.repo

import android.content.Context
import by.ivan.CafeApp.data.datasource.OrderLocalDatasource
import by.ivan.CafeApp.data.datasource.OrderRemoteDatasource
import by.ivan.CafeApp.data.local.entity.OrderLocalModel
import by.ivan.CafeApp.data.local.entity.toLocalModel
import by.ivan.CafeApp.data.remote.model.OrderRemoteModelList
import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.OrderDetails
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRepository @Inject constructor(
    private val orderLocalDatasource: OrderLocalDatasource,
    private val orderRemoteDatasource: OrderRemoteDatasource,
    @ApplicationContext private val context: Context
) {
    suspend fun getOrders(): Flow<List<OrderLocalModel>> {
        return orderLocalDatasource.getAll()
    }

    suspend fun insert(order: Order) {

    }

    suspend fun searchNewOrder(tableId: Int): NetworkResponse<OrderRemoteModelList, ResponseErrorMessage> {
        val result = orderRemoteDatasource.getOrdersByTable(tableId = tableId)
        if (result is NetworkResponse.Success) {
            insertData(orders = result.body.items)
        }

        return result
    }

    private suspend fun insertData(orders: List<OrderRemoteModelList.OrderRemoteModel>) {
        orderLocalDatasource.removeAll()
        orderLocalDatasource.saveOrders(orders.map {
            it.toLocalModel()
        })
    }

//    suspend fun getOrdersByTable(tableId: Int): Flow<List<Order>> {
//        if (connectionStateRepository.checkForInternet(context)) {
//            val ordersRemote = orderRemoteDatasource.getOrdersByTable(tableId = tableId).items
//            orderLocalDatasource.removeAll()
//            orderLocalDatasource.saveOrders(ordersRemote.map {
//                OrderLocalModel(
//                    id = it.id,
//                    orderDetails = "${it.orderDetails.id}-${it.orderDetails.menuItemsIdsText}",
//                    tableId = it.tableId,
//                    timestamp = it.timestamp,
//                )
//            })
//            return orderLocalDatasource.getAll().map { list ->
//                list.map {
//                    Order(
//                        id = it.id,
//
//                        orderDetails = OrderDetails(
//                            id = it.orderDetails.split("-")[0].toInt(),
//                            menuItemsIdsText = it.orderDetails.split("-")[1],
//                        ),
//                        tableId = it.tableId,
//                        timestamp = it.timestamp
//                    )
//                }
//            }
//        } else {
//            return orderLocalDatasource.getAll().map { list ->
//                list.map {
//                    Order(
//                        id = it.id,
//                        orderDetails = OrderDetails(
//                            id = it.orderDetails.split("-")[0].toInt(),
//                            menuItemsIdsText = it.orderDetails.split("-")[1],
//                        ),
//                        tableId = it.tableId,
//                        timestamp = it.timestamp
//                    )
//                }
//            }
//        }
//    }

    suspend fun getOrderDetailsById(orderDetailsId: Int): OrderDetails {
        with(orderRemoteDatasource.getOrderDetailsById(orderDetailsId = orderDetailsId)) {
            return OrderDetails(
                id = this.id,
                menuItemsIdsText = this.menuItemsIdsText
            )
        }
    }
}