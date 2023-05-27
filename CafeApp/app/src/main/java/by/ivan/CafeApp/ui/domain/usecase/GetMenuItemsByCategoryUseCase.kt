package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class GetMenuItemsByCategoryUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository){
    suspend operator fun invoke(categoryId: Int) = withContext(Dispatchers.IO){
        return@withContext menuItemRepository.getMenuItemsByCategory(categoryId)
    }
}