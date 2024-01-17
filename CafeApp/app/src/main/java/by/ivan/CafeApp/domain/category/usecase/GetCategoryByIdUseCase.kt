package by.ivan.CafeApp.domain.category.usecase

import by.ivan.CafeApp.data.repo.CategoryRepository
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class GetCategoryByIdUseCase @Inject constructor(private val categoryRepository: CategoryRepository) {
//    suspend operator fun invoke(id: Int): Flow<Category> = withContext(Dispatchers.IO){
//        return@withContext categoryRepository.getById(id = id)
//    }
}