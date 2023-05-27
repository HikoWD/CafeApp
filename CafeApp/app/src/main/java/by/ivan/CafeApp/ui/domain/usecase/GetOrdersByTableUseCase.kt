package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.models.Order
import by.ivan.CafeApp.ui.data.repo.OrderRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetOrdersByTableUseCase @Inject constructor(private val orderRepository: OrderRepository){
    suspend operator fun invoke(tableId: Int): List<Order> = withContext(Dispatchers.IO){
        return@withContext orderRepository.getOrdersByTable(tableId = tableId)
    }
}