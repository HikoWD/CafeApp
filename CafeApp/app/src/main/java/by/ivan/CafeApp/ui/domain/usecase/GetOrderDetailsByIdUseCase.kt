package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.models.OrderDetails
import by.ivan.CafeApp.ui.data.repo.OrderRepository
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