package by.ivan.CafeApp.domain.category.usecase

import by.ivan.CafeApp.ui.data.repo.CategoryRepository
import by.ivan.CafeApp.ui.domain.category.model.Category
import by.ivan.CafeApp.ui.domain.category.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): Flow<List<Category>> = withContext(Dispatchers.IO) {
        return@withContext categoryRepository.getLocalCategories()
            .map { list -> list.map { it.toDomain() } }
    }
}