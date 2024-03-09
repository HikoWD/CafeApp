package by.ivan.CafeApp.domain.order.usecase

import by.ivan.CafeApp.data.repo.OrderRepository
import by.ivan.CafeApp.domain.result.CompletableResult
import by.ivan.CafeApp.domain.table.model.Table
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class LoadOrdersByTableUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(table: Table): CompletableResult = withContext(Dispatchers.IO) {
        return@withContext when (val result = orderRepository.loadOrdersByTable(tableId = table.id)) {
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