package by.ivan.CafeApp.ui.data.datasource

import by.ivan.CafeApp.ui.data.remote.model.CategoryList
import by.ivan.CafeApp.ui.data.remote.util.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject


import javax.inject.Singleton

@Singleton
class CategoryRemoteDatasource @Inject constructor(private val apiService: ApiService) {
    suspend fun getCategories(): CategoryList = withContext(Dispatchers.IO){
        return@withContext apiService.getCategories()
    }
}