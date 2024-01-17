package by.ivan.CafeApp.domain.cart.usecase

import by.ivan.CafeApp.data.repo.CartItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class RemoveCartItemsUseCase @Inject constructor(private val cartItemRepository: CartItemRepository) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        cartItemRepository.removeAll()
    }
}