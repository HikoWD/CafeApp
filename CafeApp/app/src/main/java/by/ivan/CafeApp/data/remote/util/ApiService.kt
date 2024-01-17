package by.ivan.CafeApp.data.remote.util

import by.ivan.CafeApp.data.remote.model.*
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.OrderParams
import by.ivan.CafeApp.domain.table.model.Table
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface ApiService {
    @GET("/api/category")
    suspend fun getCategories(): NetworkResponse<CategoryRemoteModelList, ResponseErrorMessage>

    @GET("/api/table")
    suspend fun getTables(): NetworkResponse<TableRemoteModelList, ResponseErrorMessage>

    @GET("/api/menuitem")
    suspend fun getMenuItems(): NetworkResponse<MenuItemRemoteModelList, ResponseErrorMessage>

    @GET("/api/version")
    suspend fun getTableVersions(): TableVersionRemoteModelList

    @GET("/api/order/table/{id}")
    suspend fun getOrdersByTable(@Path("id") tableId: Int): NetworkResponse<OrderRemoteModelList, ResponseErrorMessage>

    @GET("/api/order/table/{id}")
    suspend fun getOrderDetailsById(@Path("id") orderDetailsId: Int): OrderDetailsRemoteModel

    @POST("/api/order")
    suspend fun postCartItems(@Body orderParams: OrderParams): NetworkResponse<Order, ResponseErrorMessage>

    @PUT("/api/table")
    suspend fun editTableState(@Body table: Table)
}