package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.ui.data.local.dao.TableDao
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableLocalDatasource @Inject constructor(private val tableDao: TableDao) {

    suspend fun getTables(): Flow<List<TableLocalModel>> = withContext(Dispatchers.IO){
        return@withContext tableDao.observeAll()
    }
    suspend fun saveTables(tableLocalModel: List<TableLocalModel>) = withContext(Dispatchers.IO){
        return@withContext tableDao.saveTables(tableLocalModel)
    }

    suspend fun remove(tableLocalModel: TableLocalModel) = withContext(Dispatchers.IO){
        tableDao.remove(tableLocalModel = tableLocalModel)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO){
        tableDao.removeAll()
    }
}