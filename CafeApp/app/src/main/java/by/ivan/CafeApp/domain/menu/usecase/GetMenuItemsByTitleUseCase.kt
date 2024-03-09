package by.ivan.CafeApp.domain.menu.usecase

import by.ivan.CafeApp.data.repo.MenuItemRepository
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetMenuItemsByTitleUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository) {
    suspend operator fun invoke(title: String): List<MenuItem> = withContext(Dispatchers.IO) {
        return@withContext menuItemRepository.getMenuItemByTitle(title = title)
            .map { it.toDomain() }
    }
}