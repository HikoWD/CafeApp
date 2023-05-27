package by.ivan.CafeApp.ui.data.repo

import android.content.Context
import by.ivan.CafeApp.ui.data.datasource.TableLocalDatasource
import by.ivan.CafeApp.ui.data.datasource.TableRemoteDatasource
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel
import by.ivan.CafeApp.ui.data.local.entity.TableVersionLocalModel
import by.ivan.CafeApp.ui.data.models.State
import by.ivan.CafeApp.ui.data.models.Table
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TableRepository @Inject constructor(
    private val tableRemoteDatasource: TableRemoteDatasource,
    private val tableLocalDatasource: TableLocalDatasource,
    private val tableVersionsRepository: TableVersionsRepository,
    private val connectionStateRepository: ConnectionStateRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun getTables(): List<Table> {
        if(connectionStateRepository.checkForInternet(context)){
            var remoteVersion = tableVersionsRepository.getRemoteTableVersion("Table")
            var localVersion = tableVersionsRepository.getLocalTableVersion("Table")

            if (localVersion.version == -1) {
                val tablesRemote = tableRemoteDatasource.getTables().items
                val tableLocalModel = tablesRemote.map {
                    TableLocalModel(
                        id = it.id,
                        title = it.title,
                        state = State.values().first { vals ->  vals.i== it.state}
                    )
                }

                tableLocalDatasource.removeAll()
                tableLocalDatasource.saveTables(tableLocalModel)

                remoteVersion.apply {
                    tableVersionsRepository.insert(
                        TableVersionLocalModel
                            (id = this.id, tableName = this.tableName, version = this.version)
                    )
                }
            } else if ((remoteVersion.version != -1 && remoteVersion.version != localVersion.version)) {
                val tablesRemote = tableRemoteDatasource.getTables().items
                val tableLocalModel = tablesRemote.map {
                    TableLocalModel(
                        id = it.id,
                        title = it.title,
                        state = State.valueOf(it.state.toString())
                    )
                }

                tableLocalDatasource.removeAll()
                tableLocalDatasource.saveTables(tableLocalModel)

                localVersion.version = remoteVersion.version
                tableVersionsRepository.edit(localVersion)
            }


            val tables = tableLocalDatasource.getTables().map {
                Table(
                    id = it.id,
                    title = it.title,
                    state = 0
                )
            }

            return tables
        }
        else{
            val tables = tableLocalDatasource.getTables().map {
                Table(
                    id = it.id,
                    title = it.title,
                    state = 0
                )
            }

            return tables
        }
    }
}