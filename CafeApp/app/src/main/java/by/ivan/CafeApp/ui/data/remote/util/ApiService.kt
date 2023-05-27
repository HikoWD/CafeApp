package by.ivan.CafeApp.ui.data.remote.util

import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.models.OrderParams
import by.ivan.CafeApp.ui.data.models.Table
import by.ivan.CafeApp.ui.data.remote.model.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {
    @GET("/api/category")
    suspend fun getCategories(): CategoryList

    @GET("/api/table")
    suspend fun getTables(): TableList

    @GET("/api/menuitem")
    suspend fun getMenuItems(): MenuItemList

    @GET("/api/version")
    suspend fun getTableVersions(): TableVersionList

    @GET("/api/order/table/{id}")
    suspend fun getOrdersByTable(@Path("id") tableId: Int): OrderList

    @GET("/api/order/table/{id}")
    suspend fun getOrderDetailsById(@Path("id") orderDetailsId: Int): OrderDetailsRemote

    @POST("/api/order")
    suspend fun postMenuItems(@Body orderParams: OrderParams): by.ivan.CafeApp.ui.data.models.Order
}