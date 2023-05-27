package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.remote.model.TableList
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getTables(): TableList = withContext(Dispatchers.IO){
        return@withContext apiService.getTables()
    }
}