package by.ivan.CafeApp.data.repo

import by.ivan.CafeApp.data.datasource.SearchHistoryLocalDatasource
import by.ivan.CafeApp.data.local.entity.SearchHistoryItemLocalModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchHistoryRepository @Inject constructor(private val searchHistoryLocalDatasource: SearchHistoryLocalDatasource) {
    suspend fun getAll(): Flow<List<SearchHistoryItemLocalModel>> {
        return searchHistoryLocalDatasource.observeAll()
    }

    suspend fun add(searchHistoryItemLocalModel: SearchHistoryItemLocalModel){
        searchHistoryLocalDatasource.add(searchHistoryItemLocalModel = searchHistoryItemLocalModel)
    }
}