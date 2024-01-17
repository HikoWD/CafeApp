package by.ivan.CafeApp.domain.cart.usecase

import by.ivan.CafeApp.data.repo.CartItemRepository
import by.ivan.CafeApp.data.repo.MenuItemRepository
import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.cart.model.toDomain
import by.ivan.CafeApp.domain.menu.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetByIdCartItemUseCase @Inject constructor(
    private val cartItemRepository: CartItemRepository,
    private val menuItemRepository: MenuItemRepository
) {
    suspend operator fun invoke(id: Int): Flow<CartItem> = withContext(Dispatchers.IO) {
        return@withContext cartItemRepository.getById(id = id).map {
            val menuItemLocalModel = menuItemRepository.getMenuItemById(id = it.menuItemId)
            it.toDomain(menuItem = menuItemLocalModel.toDomain())
        }
    }
}