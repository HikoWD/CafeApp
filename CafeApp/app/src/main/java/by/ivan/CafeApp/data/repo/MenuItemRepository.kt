package by.ivan.CafeApp.data.repo

import android.content.Context
import by.ivan.CafeApp.data.datasource.MenuItemLocalDatasource
import by.ivan.CafeApp.data.datasource.MenuItemRemoteDatasource
import by.ivan.CafeApp.data.local.entity.MenuItemLocalModel
import by.ivan.CafeApp.data.local.entity.toLocalModel
import by.ivan.CafeApp.data.remote.model.MenuItemRemoteModelList
import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.domain.order.model.Order
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemRepository @Inject constructor(
    private val menuItemLocalDatasource: MenuItemLocalDatasource,
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource,
    private val tableVersionsRepository: TableVersionsRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun getMenuItems(): Flow<List<MenuItemLocalModel>> {
        return menuItemLocalDatasource.getMenuItems()
    }

    suspend fun searchNewMenuItem(): NetworkResponse<MenuItemRemoteModelList, ResponseErrorMessage> {
        val result = menuItemRemoteDatasource.getMenuItems()
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

            val menuItems = menuItemLocalDatasource.getMenuItems().firstOrNull()
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

    suspend fun getMenuItemByTitle(title: String): Flow<List<MenuItemLocalModel>> {
        return menuItemLocalDatasource.getMenuItemsByTitle(title = title)
    }

    suspend fun getMenuItemsSortedByAlphabet(categoryId: Int): Flow<List<MenuItemLocalModel>> {
        return menuItemLocalDatasource.getMenuItemsByCategoryId(categoryId = categoryId)
    }

    suspend fun getMenuItemsSortedByPrice(categoryId: Int): Flow<List<MenuItemLocalModel>> {
        return menuItemLocalDatasource.getMenuItemsByCategoryId(categoryId)
    }

    suspend fun getMenuItemById(id: Int): MenuItemLocalModel {
        return menuItemLocalDatasource.getMenuItemById(id = id)
    }

    suspend fun getMenuItemsByOrderItemsIds(order: Order): List<MenuItemLocalModel> {
        val menuItems: MutableList<MenuItemLocalModel> = mutableListOf()
        order.orderDetails.menuItemsIdsText
            .split(";")
            .forEach {
                menuItems.add(
                    menuItemLocalDatasource.getMenuItemById(id = it.toInt())
                )
            }

        return menuItems.toList()
    }
}