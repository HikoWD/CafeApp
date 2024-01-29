package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.local.dao.SearchHistoryDao
import by.ivan.CafeApp.data.local.entity.SearchHistoryItemLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchHistoryLocalDatasource @Inject constructor(private val searchHistoryDao: SearchHistoryDao) {
    suspend fun observeAll(): Flow<List<SearchHistoryItemLocalModel>> =
        withContext(Dispatchers.IO) {
            return@withContext searchHistoryDao.observeAll()
        }

    suspend fun add(searchHistoryItemLocalModel: SearchHistoryItemLocalModel) = withContext(Dispatchers.IO) {
        searchHistoryDao.add(item = searchHistoryItemLocalModel)
    }
}