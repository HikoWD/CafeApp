package by.ivan.CafeApp.domain.order.usecase

import by.ivan.CafeApp.data.repo.OrderRepository
import by.ivan.CafeApp.domain.order.model.OrderDetails
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetOrderDetailsByIdUseCase @Inject constructor(private val orderRepository: OrderRepository){
    suspend operator fun invoke(orderDetailsId: Int): OrderDetails = withContext(Dispatchers.IO){
        return@withContext orderRepository.getOrderDetailsById(orderDetailsId = orderDetailsId)
    }
}