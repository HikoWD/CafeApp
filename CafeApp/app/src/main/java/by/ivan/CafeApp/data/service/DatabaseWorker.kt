package by.ivan.CafeApp.data.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import by.ivan.CafeApp.domain.category.usecase.LoadCategoriesUseCase
import by.ivan.CafeApp.domain.menu.usecase.LoadMenuItemsUseCase
import by.ivan.CafeApp.domain.result.CompletableResult
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
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val loadMenuItemsUseCase: LoadMenuItemsUseCase,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val categorySearchResult = loadCategoriesUseCase()
        val menuItemSearchResult = loadMenuItemsUseCase()
        if (categorySearchResult == CompletableResult.Success && menuItemSearchResult == CompletableResult.Success) {
            return@withContext Result.success()
        } else {
            Result.retry()
        }
    }
}