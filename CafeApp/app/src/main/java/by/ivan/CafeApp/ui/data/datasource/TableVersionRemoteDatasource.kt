package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.models.Table
import by.ivan.CafeApp.ui.data.remote.model.TableVersionList
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableVersionRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getTableVersions(tableName: String): TableVersionList.TableVersion =
        withContext(Dispatchers.IO) {
            for (tableVersion in apiService.getTableVersions().items) {
                if (tableVersion.tableName == tableName) {
                    return@withContext tableVersion
                }
            }
            return@withContext TableVersionList.TableVersion(
                id = -1,
                tableName = tableName,
                version = -1
            )
        }
}
