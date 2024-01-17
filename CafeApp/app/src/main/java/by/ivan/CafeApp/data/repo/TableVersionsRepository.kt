package by.ivan.CafeApp.data.repo

import by.ivan.CafeApp.ui.data.datasource.TableVersionLocalDatasource
import by.ivan.CafeApp.ui.data.datasource.TableVersionRemoteDatasource
import by.ivan.CafeApp.ui.data.local.entity.TableVersionLocalModel
import by.ivan.CafeApp.ui.data.remote.model.TableVersionRemoteModelList
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableVersionsRepository @Inject constructor(
    private val tableVersionLocalDatasource: TableVersionLocalDatasource,
    private val tableVersionRemoteDatasource: TableVersionRemoteDatasource
) {
    suspend fun getRemoteTableVersion(tableName: String): TableVersionRemoteModelList.TableVersionRemoteModel {
        return tableVersionRemoteDatasource.getTableVersions(tableName)
    }

    suspend fun getLocalTableVersion(tableName: String): TableVersionLocalModel {
       return tableVersionLocalDatasource.getTableVersion(tableName)
    }

    suspend fun insert(tableVersionLocalModel: TableVersionLocalModel){
        tableVersionLocalDatasource.insert(tableVersionLocalModel)
    }

    suspend fun edit(tableVersionLocalModel: TableVersionLocalModel){
        tableVersionLocalDatasource.edit(tableVersionLocalModel)
    }
}