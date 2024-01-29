package by.ivan.CafeApp.domain.menu.usecase

import by.ivan.CafeApp.data.repo.MenuItemRepository
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.model.toDomain
import by.ivan.CafeApp.domain.order.model.Order
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetMenuItemsByOrderItemsIdsUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository) {
    suspend operator fun invoke(order: Order): Flow<List<MenuItem>> = withContext(Dispatchers.IO) {
        val menuItemsIds = order.orderDetails.menuItemsIdsText.split(";").map { it.toInt() }

        return@withContext menuItemRepository.getAll().map { menuItemsLocalModel ->
            menuItemsLocalModel
                .filter { it.id in menuItemsIds }
                .map { it.toDomain() }
        }
    }
}