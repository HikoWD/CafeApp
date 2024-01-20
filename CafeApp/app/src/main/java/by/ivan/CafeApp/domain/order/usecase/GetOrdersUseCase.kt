package by.ivan.CafeApp.domain.order.usecase

import by.ivan.CafeApp.data.repo.OrderRepository
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@ViewModelScoped
class GetOrdersUseCase @Inject constructor(private val orderRepository: OrderRepository) {
    suspend operator fun invoke(): Flow<List<Order>> {
        return orderRepository.getOrders().map { ordersLocalModel ->
            ordersLocalModel.map {
                it.toDomain()
            }
        }
    }
}