package by.ivan.CafeApp.ui.data.repo

import android.content.Context
import by.ivan.CafeApp.ui.data.datasource.CategoryLocalDatasource
import by.ivan.CafeApp.ui.data.datasource.CategoryRemoteDatasource
import by.ivan.CafeApp.ui.data.local.entity.CategoryLocalModel
import by.ivan.CafeApp.ui.data.local.entity.TableVersionLocalModel
import by.ivan.CafeApp.ui.data.models.Category
import dagger.hilt.android.qualifiers.ApplicationContext

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryRemoteDatasource: CategoryRemoteDatasource,
    private val categoryLocalDatasource: CategoryLocalDatasource,
    private val tableVersionsRepository: TableVersionsRepository,
    private val connectionStateRepository: ConnectionStateRepository,
    @ApplicationContext private val context: Context,
) {
    suspend fun getCategories(): List<Category> {
        if (connectionStateRepository.checkForInternet(context)) {
            var remoteVersion = tableVersionsRepository.getRemoteTableVersion("Category")
            var localVersion = tableVersionsRepository.getLocalTableVersion("Category")
            if (localVersion.version == -1) {
                val categoriesRemoteItems = categoryRemoteDatasource.getCategories().items
                val categoryLocalModel: List<CategoryLocalModel> = categoriesRemoteItems.map {
                    CategoryLocalModel(it.id, it.title)
                }

                categoryLocalDatasource.removeAll()
                categoryLocalDatasource.saveCategories(categoryLocalModel)

                remoteVersion.apply {
                    tableVersionsRepository.insert(
                        TableVersionLocalModel
                            (id = this.id, tableName = this.tableName, version = this.version)
                    )
                }
            } else if ((remoteVersion.version != -1 && remoteVersion.version != localVersion.version)) {
                val categoriesRemoteItems = categoryRemoteDatasource.getCategories().items
                val categoryLocalModel: List<CategoryLocalModel> = categoriesRemoteItems.map {
                    CategoryLocalModel(it.id, it.title)
                }

                categoryLocalDatasource.removeAll()
                categoryLocalDatasource.saveCategories(categoryLocalModel)

                localVersion.version = remoteVersion.version
                tableVersionsRepository.edit(localVersion)
            }

            return categoryLocalDatasource.getCategories().map {
                Category(
                    id = it.id,
                    title = it.title
                )
            }
        } else {
            val categoriesLocal = categoryLocalDatasource.getCategories()

            return categoriesLocal.map {
                Category(
                    id = it.id,
                    title = it.title
                )
            }
        }
    }

    suspend fun getById(id: Int): Category{
        with(categoryLocalDatasource.getById(id = id)){
          return Category(
              id = id,
              title = title
          )
        }
    }
}