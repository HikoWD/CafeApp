package by.ivan.CafeApp.domain.table.usecase

import by.ivan.CafeApp.data.local.datastore.table.DataStoreTable
import by.ivan.CafeApp.domain.table.model.Table
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

@ViewModelScoped
class SaveTableUseCase @Inject constructor(private val dataStoreTable: DataStoreTable) {
    suspend operator fun invoke(table: Table) = withContext(Dispatchers.IO){
        dataStoreTable.saveTable(table = table)
    }
}