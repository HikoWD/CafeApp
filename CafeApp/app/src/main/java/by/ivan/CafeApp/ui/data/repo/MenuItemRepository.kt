package by.ivan.CafeApp.ui.data.repo

import android.content.Context
import by.ivan.CafeApp.ui.data.datasource.MenuItemLocalDatasource
import by.ivan.CafeApp.ui.data.datasource.MenuItemRemoteDatasource
import by.ivan.CafeApp.ui.data.local.entity.MenuItemLocalModel
import by.ivan.CafeApp.ui.data.local.entity.TableVersionLocalModel
import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.models.OrderParams
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemRepository @Inject constructor(
    private val menuItemLocalDatasource: MenuItemLocalDatasource,
    private val menuItemRemoteDatasource: MenuItemRemoteDatasource,
    private val tableVersionsRepository: TableVersionsRepository,
    private val connectionStateRepository: ConnectionStateRepository,
    @ApplicationContext private val context: Context
) {
    suspend fun getMenuItems() {
        if (connectionStateRepository.checkForInternet(context)) {
            var localVersion = tableVersionsRepository.getLocalTableVersion("MenuItem")
            var remoteVersion = tableVersionsRepository.getRemoteTableVersion("MenuItem")

            if (localVersion.version == -1) {
                val menuItemsRemote = menuItemRemoteDatasource.getMenuItem().items
                val menuItemLocalModel: List<MenuItemLocalModel> = menuItemsRemote.map {
                    MenuItemLocalModel(
                        id = it.id,
                        image = it.image,
                        categoryId = it.categoryId,
                        title = it.title,
                        description = it.description,
                        price = it.price,
                        weight = it.weight
                    )
                }

                menuItemLocalDatasource.removeAll()

                menuItemLocalDatasource.saveMenuItems(menuItemLocalModel)

                remoteVersion.apply {
                    tableVersionsRepository.insert(
                        TableVersionLocalModel
                            (id = this.id, tableName = this.tableName, version = this.version)
                    )
                }
            } else if ((remoteVersion.version != -1 && remoteVersion.version != localVersion.version)) {
                val menuItemsRemote = menuItemRemoteDatasource.getMenuItem().items
                val menuItemLocalModel: List<MenuItemLocalModel> = menuItemsRemote.map {
                    MenuItemLocalModel(
                        id = it.id,
                        image = it.image,
                        categoryId = it.categoryId,
                        title = it.title,
                        description = it.description,
                        price = it.price,
                        weight = it.weight
                    )
                }

                menuItemLocalDatasource.removeAll()
                menuItemLocalDatasource.saveMenuItems(menuItemLocalModel)

                localVersion.version = remoteVersion.version
                tableVersionsRepository.edit(localVersion)
            }
        }
    }

    suspend fun getMenuItemsByCategory(id: Int): List<MenuItem> {
        val menuItemsLocal = menuItemLocalDatasource.getMenuItemsByCategory(categoryId = id)

        val menuItems = menuItemsLocal.map {
            MenuItem(
                categoryId = it.categoryId,
                image = it.image,
                description = it.description,
                id = it.id,
                price = it.price,
                title = it.title,
                weight = it.weight
            )
        }
        return menuItems
    }

    suspend fun getMenuItemByTitle(title: String): List<MenuItem> {
        val menuItemLocal = menuItemLocalDatasource.getMenuItemsByTitle(title)
        return menuItemLocal.map {
            MenuItem(
                categoryId = it.categoryId,
                image = it.image,
                description = it.description,
                id = it.id,
                price = it.price,
                title = it.title,
                weight = it.weight
            )
        }
    }

    suspend fun postMenuItems(orderParams: OrderParams): by.ivan.CafeApp.ui.data.models.Order {
        return menuItemRemoteDatasource.postMenuItems(orderParams)
    }

    suspend fun getMenuItemsSortedByAlphabet(categoryId: Int): List<MenuItem> {
        val menuItemsLocal = menuItemLocalDatasource.getMenuItemsByCategory(categoryId)
        val menuItems = menuItemsLocal.map {
            MenuItem(
                categoryId = it.categoryId,
                image = it.image,
                description = it.description,
                id = it.id,
                price = it.price,
                title = it.title,
                weight = it.weight
            )
        }

        return menuItems.sortedBy { it.title }
    }

    suspend fun getMenuItemsSortedByPrice(categoryId: Int): List<MenuItem> {
        val menuItemsLocal = menuItemLocalDatasource.getMenuItemsByCategory(categoryId)
        val menuItems = menuItemsLocal.map {
            MenuItem(
                categoryId = it.categoryId,
                image = it.image,
                description = it.description,
                id = it.id,
                price = it.price,
                title = it.title,
                weight = it.weight
            )
        }

        return menuItems.sortedBy { it.price }.reversed()
    }

    suspend fun getMenuItemById(id: Int): MenuItem {
        with(menuItemLocalDatasource.getMenuItemById(id = id)) {
            return MenuItem(
                categoryId = this.categoryId,
                image = this.image,
                description = this.description,
                id = this.id,
                price = this.price,
                title = this.title,
                weight = this.weight
            )
        }
    }
}
