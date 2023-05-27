package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.local.dao.CategoryDao
import by.ivan.CafeApp.ui.data.local.entity.CategoryLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryLocalDatasource @Inject constructor(private val categoryDao: CategoryDao) {
    suspend fun getCategories(): List<CategoryLocalModel> = withContext(Dispatchers.IO){
        return@withContext categoryDao.getAll()
    }

    suspend fun removeAll() = withContext(Dispatchers.IO){
        categoryDao.removeAll()
    }

    suspend fun getById(id: Int): CategoryLocalModel = withContext(Dispatchers.IO){
        return@withContext categoryDao.getById(id = id)
    }


    suspend fun saveCategories(categoryLocalModel: List<CategoryLocalModel>) = withContext(Dispatchers.IO){
        categoryDao.saveCategories(categoryLocalModel)
    }
}