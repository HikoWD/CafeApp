package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.remote.model.TableVersionRemoteModelList
import by.ivan.CafeApp.data.remote.util.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableVersionRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun loadTableVersions(tableName: String): TableVersionRemoteModelList.TableVersionRemoteModel =
        withContext(Dispatchers.IO) {
            for (tableVersion in apiService.loadTableVersions().items) {
                if (tableVersion.tableName == tableName) {
                    return@withContext tableVersion
                }
            }
            return@withContext TableVersionRemoteModelList.TableVersionRemoteModel(
                id = -1,
                tableName = tableName,
                version = -1
            )
        }
}
