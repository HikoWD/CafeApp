package by.ivan.CafeApp.domain.search_history.usecase

import by.ivan.CafeApp.data.repo.SearchHistoryRepository
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import by.ivan.CafeApp.domain.search_history.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetAllSearchHistoryItemsUseCase @Inject constructor(private val searchHistoryRepository: SearchHistoryRepository) {
    suspend operator fun invoke(): Flow<List<SearchHistoryItem>> = withContext(Dispatchers.IO) {
        return@withContext searchHistoryRepository.getAll().map { searchHistoryItemsLocalModel ->
            searchHistoryItemsLocalModel.map {
                it.toDomain()
            }
        }
    }
}