package by.ivan.CafeApp.domain.table.usecase

import by.ivan.CafeApp.data.repo.TableRepository
import by.ivan.CafeApp.domain.table.model.Table
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class EditTableStateUseCase @Inject constructor(private val tableRepository: TableRepository) {
    suspend operator fun invoke(table: Table) = withContext(Dispatchers.IO){
        tableRepository.editTableState(table = table)
    }
}