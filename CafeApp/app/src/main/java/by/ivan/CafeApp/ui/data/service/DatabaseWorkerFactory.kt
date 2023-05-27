package by.ivan.CafeApp.ui.data.service

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import by.ivan.CafeApp.ui.data.repo.CategoryRepository
import by.ivan.CafeApp.ui.data.repo.MenuItemRepository
import by.ivan.CafeApp.ui.data.repo.TableRepository
import by.ivan.CafeApp.ui.domain.usecase.GetCategoriesUseCase
import by.ivan.CafeApp.ui.domain.usecase.GetMenuItemUseCase
import by.ivan.CafeApp.ui.domain.usecase.GetTablesUseCase
import javax.inject.Inject

class DatabaseWorkerFactory @Inject constructor(
    private val categoryRepository: CategoryRepository,
    private val menuItemRepository: MenuItemRepository,
    private val tableRepository: TableRepository
) : WorkerFactory() {
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): CoroutineWorker = DatabaseWorker(
        appContext,
        workerParameters,
        categoryRepository,
        menuItemRepository,
        tableRepository,
    )
}