package by.ivan.CafeApp.data.repo

import android.content.Context
import by.ivan.CafeApp.ui.data.datasource.TableLocalDatasource
import by.ivan.CafeApp.ui.data.datasource.TableRemoteDatasource
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel
import by.ivan.CafeApp.ui.data.local.entity.toLocalModel
import by.ivan.CafeApp.ui.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.ui.data.remote.model.TableRemoteModelList
import by.ivan.CafeApp.ui.domain.table.model.Table
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepository @Inject constructor(
    private val tableRemoteDatasource: TableRemoteDatasource,
    private val tableLocalDatasource: TableLocalDatasource,
    private val tableVersionsRepository: TableVersionsRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun getTables(): Flow<List<TableLocalModel>> {
        return tableLocalDatasource.getTables()
    }

    suspend fun searchNewTable(): NetworkResponse<TableRemoteModelList, ResponseErrorMessage> {
        val result = tableRemoteDatasource.getTables()
        if (result is NetworkResponse.Success) {
            insertData(tablesRemote = result.body.items)
        }

        return result
    }

    private suspend fun insertData(tablesRemote: List<TableRemoteModelList.TableRemoteModel>) {
        var remoteVersion = tableVersionsRepository.getRemoteTableVersion("Table")
        var localVersion = tableVersionsRepository.getLocalTableVersion("Table")

        if (localVersion.version == -1) {
            val tableLocalModel = tablesRemote.map {
                it.toLocalModel()
            }

            //tableLocalDatasource.removeAll()
            tableLocalDatasource.saveTables(tableLocalModel)

            remoteVersion.apply {
                tableVersionsRepository.insert(
                    this.toLocalModel()
                )
            }
        } else if ((remoteVersion.version != -1 && remoteVersion.version != localVersion.version)) {
            val tableLocalModel = tablesRemote.map {
                it.toLocalModel()
            }

            //tableLocalDatasource.removeAll()

            val tables = tableLocalDatasource.getTables().firstOrNull()
            if(tables?.count() != tableLocalModel.count()){
                tables?.forEach {
                    if(!tableLocalModel.contains(it)){
                        tableLocalDatasource.remove(tableLocalModel = it)
                    }
                }
            }

            tableLocalDatasource.saveTables(tableLocalModel)

            localVersion.version = remoteVersion.version
            tableVersionsRepository.edit(localVersion)
        }
    }

    suspend fun editTableState(table: Table) {
        tableRemoteDatasource.editTableState(table = table)
    }
}