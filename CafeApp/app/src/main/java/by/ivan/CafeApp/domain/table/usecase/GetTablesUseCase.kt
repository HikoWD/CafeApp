package by.ivan.CafeApp.domain.table.usecase

import by.ivan.CafeApp.data.repo.TableRepository
import by.ivan.CafeApp.domain.table.model.Table
import by.ivan.CafeApp.domain.table.model.toDomain
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetTablesUseCase @Inject constructor(private val tableRepository: TableRepository) {
    suspend operator fun invoke(): Flow<List<Table>> = withContext(Dispatchers.IO) {
        return@withContext tableRepository.getAll().map { tablesLocalModel ->
            tablesLocalModel.map {
                it.toDomain()
            }
        }
    }
}