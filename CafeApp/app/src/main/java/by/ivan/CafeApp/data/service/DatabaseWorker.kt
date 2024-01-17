package by.ivan.CafeApp.data.service

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import by.ivan.CafeApp.domain.category.usecase.SearchNewCategoryUseCase
import by.ivan.CafeApp.domain.menu.usecase.SearchNewMenuItemUseCase
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
    private val searchNewCategoryUseCase: SearchNewCategoryUseCase,
    private val searchNewMenuItemUseCase: SearchNewMenuItemUseCase,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        val categorySearchResult = searchNewCategoryUseCase()
        val menuItemSearchResult = searchNewMenuItemUseCase()
        if (categorySearchResult == CompletableResult.Success && menuItemSearchResult == CompletableResult.Success) {
            return@withContext Result.success()
        } else {
            Result.retry()
        }
    }
}