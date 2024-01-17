package by.ivan.CafeApp.domain.cart.usecase

import by.ivan.CafeApp.data.local.entity.toCartItemLocalModel
import by.ivan.CafeApp.data.repo.CartItemRepository
import by.ivan.CafeApp.domain.menu.model.MenuItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class AddMenuItemToCartUseCase @Inject constructor(private val cartItemRepository: CartItemRepository) {
    suspend operator fun invoke(menuItem: MenuItem) = withContext(Dispatchers.IO) {
        cartItemRepository.createItemOrIncreaseCount(cartItemLocalModel = menuItem.toCartItemLocalModel(count = 1))
    }
}