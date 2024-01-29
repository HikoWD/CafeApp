package by.ivan.CafeApp.domain.search_history.usecase

import by.ivan.CafeApp.data.local.entity.toLocalModel
import by.ivan.CafeApp.data.repo.SearchHistoryRepository
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class AddSearchHistoryItemUseCase @Inject constructor(private val searchHistoryRepository: SearchHistoryRepository) {
    suspend operator fun invoke(searchHistoryItem: SearchHistoryItem) =
        withContext(Dispatchers.IO) {
            return@withContext searchHistoryRepository.add(searchHistoryItemLocalModel = searchHistoryItem.toLocalModel())
        }
}