package by.ivan.CafeApp.data.datasource

import by.ivan.CafeApp.data.local.dao.CategoryDao
import by.ivan.CafeApp.data.local.entity.CategoryLocalModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryLocalDatasource @Inject constructor(private val categoryDao: CategoryDao) {
    suspend fun getCategories(): Flow<List<CategoryLocalModel>> = withContext(Dispatchers.IO){
        return@withContext categoryDao.observeAll()
    }

    suspend fun getById(id: Int): Flow<CategoryLocalModel> = withContext(Dispatchers.IO){
        return@withContext categoryDao.getById(id = id)
    }

    suspend fun saveCategories(categories: List<CategoryLocalModel>) = withContext(Dispatchers.IO){
        categoryDao.saveCategories(categories)
    }

    suspend fun remove(categoryLocalModel: CategoryLocalModel) = withContext(Dispatchers.IO){
        categoryDao.remove(categoryLocalModel = categoryLocalModel)
    }


    suspend fun removeAll() = withContext(Dispatchers.IO){
        categoryDao.removeAll()
    }
}