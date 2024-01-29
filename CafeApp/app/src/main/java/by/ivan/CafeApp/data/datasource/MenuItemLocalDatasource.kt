package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.local.dao.MenuItemDao
import by.ivan.CafeApp.data.local.entity.MenuItemLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MenuItemLocalDatasource @Inject constructor(private val menuItemDao: MenuItemDao) {
    suspend fun getAll(): Flow<List<MenuItemLocalModel>> = withContext(Dispatchers.IO) {
        return@withContext menuItemDao.observeAll()
    }

    suspend fun saveMenuItems(menuItemLocalModel: List<MenuItemLocalModel>) =
        withContext(Dispatchers.IO) {
            return@withContext menuItemDao.saveMenuItems(menuItemLocalModel)
        }

    suspend fun getMenuItemsByTitle(title: String): Flow<List<MenuItemLocalModel>> =
        withContext(Dispatchers.IO) {
            return@withContext menuItemDao.getByTitle(title)
        }

    suspend fun getMenuItemById(id: Int): Flow<MenuItemLocalModel> = withContext(Dispatchers.IO){
        return@withContext menuItemDao.getById(id = id)
    }

    suspend fun remove(menuItemLocalModel: MenuItemLocalModel) = withContext(Dispatchers.IO) {
        menuItemDao.remove(menuItemLocalModel = menuItemLocalModel)
    }

    suspend fun removeAllByCategoryId(categoryId: Int) = withContext(Dispatchers.IO) {
        menuItemDao.removeAllByCategoryId(categoryId = categoryId)
    }

    suspend fun removeAll() = withContext(Dispatchers.IO) {
        menuItemDao.removeAll()
    }

    suspend fun getMenuItemsByCategoryId(categoryId: Int): Flow<List<MenuItemLocalModel>> =
        withContext(Dispatchers.IO) {
            return@withContext menuItemDao.getByCategoryId(categoryId = categoryId)
        }
}