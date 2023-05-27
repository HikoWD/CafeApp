package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.local.dao.TableDao
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableLocalDatasource @Inject constructor(private val tableDao: TableDao) {

    suspend fun getTables(): List<TableLocalModel> = withContext(Dispatchers.IO){
        return@withContext tableDao.getAll()
    }
    suspend fun saveTables(tableLocalModel: List<TableLocalModel>) = withContext(Dispatchers.IO){
        return@withContext tableDao.saveTables(tableLocalModel)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO){
        tableDao.removeAll()
    }
}