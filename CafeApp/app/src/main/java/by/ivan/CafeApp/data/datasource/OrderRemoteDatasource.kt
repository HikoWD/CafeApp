package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.remote.model.OrderDetailsRemoteModel
import by.ivan.CafeApp.data.remote.model.OrderRemoteModelList
import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.data.remote.util.ApiService
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OrderRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getOrdersByTable(tableId: Int): NetworkResponse<OrderRemoteModelList, ResponseErrorMessage> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.getOrdersByTable(tableId = tableId)
        }

    suspend fun getOrderDetailsById(orderDetailsId: Int): OrderDetailsRemoteModel =
        withContext(Dispatchers.IO) {
            return@withContext apiService.getOrderDetailsById(orderDetailsId = orderDetailsId)
        }
}