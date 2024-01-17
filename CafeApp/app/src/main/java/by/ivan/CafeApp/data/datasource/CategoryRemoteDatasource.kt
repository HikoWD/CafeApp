package by.ivan.CafeApp.data.datasource


import by.ivan.CafeApp.ui.data.remote.model.CategoryRemoteModelList
import by.ivan.CafeApp.ui.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getCategories(): NetworkResponse<CategoryRemoteModelList, ResponseErrorMessage> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.getCategories()
        }
}