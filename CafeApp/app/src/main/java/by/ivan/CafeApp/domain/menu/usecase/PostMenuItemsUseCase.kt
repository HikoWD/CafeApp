package by.ivan.CafeApp.domain.menu.usecase

import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class PostMenuItemsUseCase @Inject constructor(private val menuItemsRepository: MenuItemRepository) {
//    suspend operator fun invoke(cartItems: List<CartItem>): PublicationOrderResult =
//        withContext(Dispatchers.IO) {
//            return@withContext when (val result =
//                menuItemsRepository.postMenuItems(cartItems = cartItems)) {
//                is NetworkResponse.Success -> {
//                    PublicationOrderResult.Success(order = result.body)
//                }
//
//                is NetworkResponse.ServerError -> {
//                    val error = result.body?.error ?: "Server Error"
//                    PublicationOrderResult.Error(error)
//                }
//
//                is NetworkResponse.NetworkError -> {
//                    val error = result.body?.error ?: "Network Error"
//                    PublicationOrderResult.Error(error)
//                }
//
//                is NetworkResponse.UnknownError -> {
//                    val error = result.body?.error ?: "Unknown Error"
//                    PublicationOrderResult.Error(error)
//                }
//            }
//        }
}