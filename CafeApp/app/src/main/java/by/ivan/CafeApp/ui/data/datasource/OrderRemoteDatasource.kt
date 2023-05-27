package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.remote.model.OrderDetailsRemote
import by.ivan.CafeApp.ui.data.remote.model.OrderList
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getOrdersByTable(tableId: Int): OrderList = withContext(Dispatchers.IO){
        return@withContext apiService.getOrdersByTable(tableId = tableId)
    }

    suspend fun getOrderDetailsById(orderDetailsId: Int): OrderDetailsRemote = withContext(Dispatchers.IO){
        return@withContext apiService.getOrderDetailsById(orderDetailsId = orderDetailsId)
    }
}