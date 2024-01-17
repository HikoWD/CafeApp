package by.ivan.CafeApp.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import by.ivan.CafeApp.domain.category.usecase.SearchNewCategoryUseCase
import by.ivan.CafeApp.domain.menu.usecase.SearchNewMenuItemUseCase
import javax.inject.Inject

class DatabaseWorkerFactory @Inject constructor(
    private val searchNewCategoryUseCase: SearchNewCategoryUseCase,
    private val searchNewMenuItemUseCase: SearchNewMenuItemUseCase,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): CoroutineWorker = DatabaseWorker(
        appContext,
        workerParameters,
        searchNewCategoryUseCase,
        searchNewMenuItemUseCase,
    )
}