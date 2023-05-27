package by.ivan.CafeApp.ui.domain.usecase

import android.content.Context
import androidx.work.*
import by.ivan.CafeApp.ui.data.service.DatabaseWorker
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.time.Duration
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class StartDatabaseWorkerUseCase @Inject constructor(
    private val workManager: WorkManager,
) {
    suspend operator fun invoke() = withContext(Dispatchers.IO) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(
                NetworkType.CONNECTED
            ).build()

        val databaseWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<DatabaseWorker>(Duration.ZERO)
                .setConstraints(constraints)
                .build()

        workManager
            .enqueueUniquePeriodicWork(
                "databaseWorkRequest",
                ExistingPeriodicWorkPolicy.KEEP,
                databaseWorkRequest )
    }
}