package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.models.OrderParams
import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class PostMenuItemsUseCase @Inject constructor(private val menuItemsRepository: MenuItemRepository){
    suspend operator fun invoke(orderParams: OrderParams): by.ivan.CafeApp.ui.data.models.Order = withContext(Dispatchers.IO){
        return@withContext menuItemsRepository.postMenuItems(orderParams)
    }
}