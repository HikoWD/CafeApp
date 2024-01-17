package by.ivan.CafeApp.domain.order.usecase

import by.ivan.CafeApp.ui.domain.order.model.Order
import by.ivan.CafeApp.ui.data.repo.OrderRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetOrdersByTableUseCase @Inject constructor(private val orderRepository: OrderRepository){
    suspend operator fun invoke(tableId: Int): Flow<List<Order>> = withContext(Dispatchers.IO){
        return@withContext flow{}
        //orderRepository.getOrdersByTable(tableId = tableId)
    }
}