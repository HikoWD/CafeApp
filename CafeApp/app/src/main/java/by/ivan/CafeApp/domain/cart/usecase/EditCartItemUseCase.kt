package by.ivan.CafeApp.domain.cart.usecase

import by.ivan.CafeApp.data.local.entity.toLocalModel
import by.ivan.CafeApp.data.repo.CartItemRepository
import by.ivan.CafeApp.domain.cart.model.CartItem
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

//@ViewModelScoped
//class EditCartItemUseCase @Inject constructor(private val cartItemRepository: CartItemRepository) {
//    suspend operator fun invoke(cartItem: CartItem) = withContext(Dispatchers.IO) {
//        cartItemRepository.increaseCount(cartItemLocalModel = cartItem.toLocalModel())
//    }
//}