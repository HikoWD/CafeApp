package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.local.dao.MenuItemDao
import by.ivan.CafeApp.ui.data.local.entity.MenuItemLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemLocalDatasource @Inject constructor(private val menuItemDao: MenuItemDao) {
    suspend fun saveMenuItems(menuItemLocalModel: List<MenuItemLocalModel>) =
        withContext(Dispatchers.IO) {
            return@withContext menuItemDao.saveMenuItems(menuItemLocalModel)
        }

    suspend fun getMenuItemsByTitle(title: String): List<MenuItemLocalModel> =
        withContext(Dispatchers.IO) {
            return@withContext menuItemDao.getByTitle(title)
        }

    suspend fun getMenuItemById(id: Int): MenuItemLocalModel = withContext(Dispatchers.IO){
        return@withContext menuItemDao.getById(id = id)
    }

    suspend fun getMenuItems(): List<MenuItemLocalModel> = withContext(Dispatchers.IO) {
        return@withContext menuItemDao.getAll()
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        menuItemDao.removeAll()
    }

    suspend fun getMenuItemsByCategory(categoryId: Int): List<MenuItemLocalModel> =
        withContext(Dispatchers.IO) {
            return@withContext menuItemDao.getByCategory(categoryId = categoryId)
        }
}