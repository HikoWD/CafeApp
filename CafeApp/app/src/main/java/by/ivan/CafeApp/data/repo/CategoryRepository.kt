package by.ivan.CafeApp.data.repo

import by.ivan.CafeApp.data.datasource.CategoryLocalDatasource
import by.ivan.CafeApp.data.datasource.CategoryRemoteDatasource
import by.ivan.CafeApp.data.datasource.MenuItemLocalDatasource
import by.ivan.CafeApp.data.local.entity.CategoryLocalModel
import by.ivan.CafeApp.data.local.entity.toLocalModel
import by.ivan.CafeApp.data.remote.model.CategoryRemoteModelList
import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryRemoteDatasource: CategoryRemoteDatasource,
    private val categoryLocalDatasource: CategoryLocalDatasource,
    private val tableVersionsRepository: TableVersionsRepository,
    private val menuItemLocalDatasource: MenuItemLocalDatasource
) {
    suspend fun getAll(): Flow<List<CategoryLocalModel>> {
        return categoryLocalDatasource.getAll()
    }

    suspend fun searchNewCategory(): NetworkResponse<CategoryRemoteModelList, ResponseErrorMessage> {
        val result = categoryRemoteDatasource.getCategories()
        if (result is NetworkResponse.Success) {
            insertData(categoriesRemote = result.body.items)
        }

        return result
    }

    //todo
    private suspend fun insertData(categoriesRemote: List<CategoryRemoteModelList.CategoryRemoteModel>) {
        val remoteVersion = tableVersionsRepository.getRemoteTableVersion("Category")
        val localVersion = tableVersionsRepository.getLocalTableVersion("Category")
        if (localVersion.version == -1) {
            val categoryLocalModel: List<CategoryLocalModel> = categoriesRemote.map {
                it.toLocalModel()
            }

            categoryLocalDatasource.saveCategories(categories = categoryLocalModel)

            remoteVersion.apply {
                tableVersionsRepository.insert(
                    this.toLocalModel()
                )
            }
        } else if ((remoteVersion.version != -1 && remoteVersion.version != localVersion.version)) {
            val categoriesLocal: List<CategoryLocalModel> = categoriesRemote.map {
                it.toLocalModel()
            }

            val categoriesFromDb = categoryLocalDatasource.getAll().firstOrNull()
            if (categoriesFromDb?.count() != categoriesLocal.count()) {
                categoriesFromDb?.forEach {
                    if (!categoriesLocal.contains(it)) {
                        menuItemLocalDatasource.removeAllByCategoryId(categoryId = it.id)
                        categoryLocalDatasource.remove(categoryLocalModel = it)
                    }
                }
            }
            categoryLocalDatasource.saveCategories(categories = categoriesLocal)

            localVersion.version = remoteVersion.version
            tableVersionsRepository.edit(localVersion)
        }
    }
}