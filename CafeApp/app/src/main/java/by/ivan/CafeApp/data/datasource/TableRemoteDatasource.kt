package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.data.remote.model.TableRemoteModelList
import by.ivan.CafeApp.data.remote.util.ApiService
import by.ivan.CafeApp.domain.table.model.Table
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun loadTables(): NetworkResponse<TableRemoteModelList, ResponseErrorMessage> =
        withContext(Dispatchers.IO) {
            return@withContext apiService.loadTables()
        }

    suspend fun editTableState(table: Table) = withContext(Dispatchers.IO) {
        apiService.editTableState(table = table)
    }
}