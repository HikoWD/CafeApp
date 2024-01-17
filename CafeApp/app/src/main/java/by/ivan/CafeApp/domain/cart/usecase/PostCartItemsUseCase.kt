package by.ivan.CafeApp.domain.cart.usecase

import by.ivan.CafeApp.data.repo.CartItemRepository
import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.result.PublicationOrderResult
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class PostCartItemsUseCase @Inject constructor(private val cartItemRepository: CartItemRepository) {
    suspend operator fun invoke(cartItems: List<CartItem>): PublicationOrderResult =
        withContext(Dispatchers.IO) {
            return@withContext when (val result =
                cartItemRepository.postCartItems(cartItems = cartItems)) {
                is NetworkResponse.Success -> {
                    PublicationOrderResult.Success(order = result.body)
                }

                is NetworkResponse.ServerError -> {
                    val error = result.body?.error ?: "Server Error"
                    PublicationOrderResult.Error(error)
                }

                is NetworkResponse.NetworkError -> {
                    val error = result.body?.error ?: "Network Error"
                    PublicationOrderResult.Error(error)
                }

                is NetworkResponse.UnknownError -> {
                    val error = result.body?.error ?: "Unknown Error"
                    PublicationOrderResult.Error(error)
                }
            }
        }
}