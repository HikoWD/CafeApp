package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.data.remote.util.ApiService
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.OrderParams
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartItemRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun postCartItems(orderParams: OrderParams): NetworkResponse<Order, ResponseErrorMessage> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.postCartItems(orderParams = orderParams)
        }
}