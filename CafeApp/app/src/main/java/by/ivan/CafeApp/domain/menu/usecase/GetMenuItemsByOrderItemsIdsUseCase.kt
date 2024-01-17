package by.ivan.CafeApp.domain.menu.usecase

import by.ivan.CafeApp.data.repo.MenuItemRepository
import by.ivan.CafeApp.domain.menu.model.toDomain
import by.ivan.CafeApp.domain.order.model.Order
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetMenuItemsByOrderItemsIdsUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository) {
    suspend operator fun invoke(order: Order) = withContext(Dispatchers.IO) {
        return@withContext menuItemRepository.getMenuItemsByOrderItemsIds(order = order).map {
            it.toDomain()
        }
    }
}