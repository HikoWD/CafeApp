package by.ivan.CafeApp.data.repo

import by.ivan.CafeApp.data.datasource.MenuItemLocalDatasource
import by.ivan.CafeApp.data.datasource.MenuItemRemoteDatasource
import by.ivan.CafeApp.data.local.entity.MenuItemLocalModel
import by.ivan.CafeApp.data.local.entity.toLocalModel
import by.ivan.CafeApp.data.remote.model.MenuItemRemoteModelList
import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemRepository @Inject constructor(
    private val menuItemLocalDatasource: MenuItemLocalDatasource,
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource,
    private val tableVersionsRepository: TableVersionsRepository
) {
    suspend fun getAll(): Flow<List<MenuItemLocalModel>> {
        return menuItemLocalDatasource.getAll()
    }

    suspend fun loadMenuItems(): NetworkResponse<MenuItemRemoteModelList, ResponseErrorMessage> {
        val result = menuItemRemoteDatasource.loadMenuItems()
        if (result is NetworkResponse.Success) {
            insertData(menuItemsRemote = result.body.items)
        }

        return result
    }

    private suspend fun insertData(menuItemsRemote: List<MenuItemRemoteModelList.MenuItemRemoteModel>) {
        var localVersion = tableVersionsRepository.getLocalTableVersion("MenuItem")
        var remoteVersion = tableVersionsRepository.getRemoteTableVersion("MenuItem")

        if (localVersion.version == -1) {
            val menuItemLocalModel: List<MenuItemLocalModel> = menuItemsRemote.map {
                it.toLocalModel()
            }

            //menuItemLocalDatasource.removeAll()
            menuItemLocalDatasource.saveMenuItems(menuItemLocalModel)

            remoteVersion.apply {
                tableVersionsRepository.insert(
                    this.toLocalModel()
                )
            }
        } else if ((remoteVersion.version != -1 && remoteVersion.version != localVersion.version)) {
            val menuItemLocalModel: List<MenuItemLocalModel> = menuItemsRemote.map {
                it.toLocalModel()
            }

            val menuItems = menuItemLocalDatasource.getAll().firstOrNull()
            if (menuItems?.count() != menuItemLocalModel.count()) {
                menuItems?.forEach {
                    if (!menuItemLocalModel.contains(it)) {
                        menuItemLocalDatasource.remove(menuItemLocalModel = it)
                    }
                }
            }
            //menuItemLocalDatasource.removeAll()
            menuItemLocalDatasource.saveMenuItems(menuItemLocalModel)

            localVersion.version = remoteVersion.version
            tableVersionsRepository.edit(localVersion)
        }
    }

    suspend fun getMenuItemsByCategoryId(id: Int): Flow<List<MenuItemLocalModel>> {
        return menuItemLocalDatasource.getMenuItemsByCategoryId(categoryId = id)
    }

    suspend fun getMenuItemByTitle(title: String): List<MenuItemLocalModel> {
        return menuItemLocalDatasource.getMenuItemsByTitle(title = title)
    }

    suspend fun getMenuItemById(id: Int): Flow<MenuItemLocalModel> {
        return menuItemLocalDatasource.getMenuItemById(id = id)
    }
}