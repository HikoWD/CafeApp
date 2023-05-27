package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.models.OrderParams
import by.ivan.CafeApp.ui.data.remote.model.MenuItemList
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getMenuItem(): MenuItemList = withContext(Dispatchers.IO){
        return@withContext apiService.getMenuItems()
    }

    suspend fun postMenuItems(orderParams: OrderParams): by.ivan.CafeApp.ui.data.models.Order = withContext(Dispatchers.IO){
        return@withContext apiService.postMenuItems(orderParams)
    }
}