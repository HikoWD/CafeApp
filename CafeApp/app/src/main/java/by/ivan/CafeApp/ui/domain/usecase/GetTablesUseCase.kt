package by.ivan.CafeApp.ui.domain.usecase

import by.ivan.CafeApp.ui.data.models.Table
import by.ivan.CafeApp.ui.data.repo.TableRepository
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetTablesUseCase @Inject constructor(private val tableRepository: TableRepository) {
    suspend operator fun invoke(): List<Table> = withContext(Dispatchers.IO){
        return@withContext tableRepository.getTables()
    }
}