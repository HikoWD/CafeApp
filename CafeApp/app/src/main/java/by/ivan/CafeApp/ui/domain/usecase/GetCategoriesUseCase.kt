package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.models.Category
import by.ivan.CafeApp.ui.data.repo.CategoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetCategoriesUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
    suspend operator fun invoke(): List<Category> = withContext(Dispatchers.IO){
        return@withContext categoryRepository.getCategories()
    }
}