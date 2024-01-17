package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.ui.data.remote.model.MenuItemRemoteModelList
import by.ivan.CafeApp.ui.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getMenuItems(): NetworkResponse<MenuItemRemoteModelList, ResponseErrorMessage> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.getMenuItems()
        }
}