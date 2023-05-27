package by.ivan.CafeApp.ui.data.service

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import by.ivan.CafeApp.ui.data.repo.CategoryRepository
import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import by.ivan.CafeApp.ui.data.repo.TableRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@HiltWorker
class DatabaseWorker @AssistedInject constructor(
    @Assisted
    private val context: Context,
    @Assisted
    private val params: WorkerParameters,
    private val categoryRepository: CategoryRepository,
    private val menuItemRepository: MenuItemRepository,
    private val tableRepository: TableRepository
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        categoryRepository.getCategories()
        menuItemRepository.getMenuItems()
        tableRepository.getTables()
        Log.d("TestWorker", "RABOTAU OK")
        return@withContext try{
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}