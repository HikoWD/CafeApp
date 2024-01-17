package by.ivan.CafeApp.domain.menu.usecase

import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import by.ivan.CafeApp.ui.domain.menu.model.MenuItem
import by.ivan.CafeApp.ui.domain.menu.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetMenuItemsByCategoryIdUseCase @Inject constructor(private val menuItemRepository: MenuItemRepository) {
    suspend operator fun invoke(categoryId: Int): Flow<List<MenuItem>> =
        withContext(Dispatchers.IO) {
            return@withContext menuItemRepository.getMenuItemsByCategoryId(categoryId)
                .map { menuItemsLocalModel ->
                    menuItemsLocalModel.map {
                        it.toDomain()
                    }
                }
        }
}