package by.ivan.CafeApp.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import by.ivan.CafeApp.domain.category.usecase.LoadCategoriesUseCase
import by.ivan.CafeApp.domain.menu.usecase.LoadMenuItemsUseCase
import javax.inject.Inject

class DatabaseWorkerFactory @Inject constructor(
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val loadMenuItemsUseCase: LoadMenuItemsUseCase,
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): CoroutineWorker = DatabaseWorker(
        appContext,
        workerParameters,
        loadCategoriesUseCase,
        loadMenuItemsUseCase,
    )
}