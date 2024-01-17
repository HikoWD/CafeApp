package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.local.dao.TableVersionDao
import by.ivan.CafeApp.data.local.entity.TableVersionLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableVersionLocalDatasource @Inject constructor(private val tableVersionDao: TableVersionDao) {

    suspend fun getTableVersion(tableName: String): TableVersionLocalModel =
        withContext(Dispatchers.IO) {
            for (tableVersion in tableVersionDao.getAll()) {
                if (tableVersion.tableName == tableName) {
                    return@withContext tableVersion
                }
            }
            return@withContext TableVersionLocalModel(
                id = -1,
                tableName = tableName,
                version = -1
            )
        }

    suspend fun insert(tableVersionLocalModel: TableVersionLocalModel) {
        tableVersionDao.insert(tableVersionLocalModel)
    }

    suspend fun edit(tableVersionLocalModel: TableVersionLocalModel) {
        tableVersionDao.edit(tableVersionLocalModel)
    }

    suspend fun saveTableVersions(tableVersionLocalModelList: List<TableVersionLocalModel>) =
        withContext(Dispatchers.IO) {
            tableVersionDao.saveTableVersions(tableVersionLocalModelList)
        }

    suspend fun removeAllTableVersions() = withContext(Dispatchers.IO) {
        tableVersionDao.removeAll()
    }
}