package by.ivan.CafeApp.domain.table.usecase

import by.ivan.CafeApp.data.repo.TableRepository
import by.ivan.CafeApp.domain.result.CompletableResult
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class SearchNewTablesUseCase @Inject constructor(private val tableRepository: TableRepository) {
    suspend operator fun invoke(): CompletableResult = withContext(Dispatchers.IO) {
        return@withContext when (val result = tableRepository.searchNewTable()) {
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