package by.ivan.CafeApp.domain.menu.usecase

import by.ivan.CafeApp.data.repo.MenuItemRepository
import by.ivan.CafeApp.domain.result.CompletableResult
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchNewMenuItemUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository) {
    suspend operator fun invoke(): CompletableResult = withContext(Dispatchers.IO) {
        return@withContext when (val result = menuItemRepository.searchNewMenuItem()) {
            is NetworkResponse.Success -> {
                CompletableResult.Success
            }

            is NetworkResponse.ServerError -> {
                val error = result.body?.error ?: "Server Error"
                CompletableResult.Error(error)
            }

            is NetworkResponse.NetworkError -> {
                val error = result.body?.error ?: "Network Error"
                CompletableResult.Error(error)
            }

            is NetworkResponse.UnknownError -> {
                val error = result.body?.error ?: "Unknown Error"
                CompletableResult.Error(error)
            }
        }
    }
}