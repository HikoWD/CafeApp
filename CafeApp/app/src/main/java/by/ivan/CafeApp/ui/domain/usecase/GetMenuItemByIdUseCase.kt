package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetMenuItemByIdUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository) {
    suspend operator fun invoke(id: Int): MenuItem = withContext(Dispatchers.IO){
        return@withContext menuItemRepository.getMenuItemById(id = id)
    }
}