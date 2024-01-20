package by.ivan.CafeApp.domain.table.usecase

import by.ivan.CafeApp.data.local.datastore.table.DataStoreTable
import by.ivan.CafeApp.domain.table.model.Table
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class GetCurrentTableUseCase @Inject constructor(private val dataStoreTable: DataStoreTable) {
    suspend operator fun invoke(): Flow<Table?> = withContext(Dispatchers.IO){
        return@withContext dataStoreTable.getCurrentTable
    }
}