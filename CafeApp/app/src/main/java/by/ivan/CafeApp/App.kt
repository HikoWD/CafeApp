package by.ivan.CafeApp

import android.app.Application
import androidx.work.Configuration
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import by.ivan.CafeApp.data.service.DatabaseWorker
import by.ivan.CafeApp.data.service.DatabaseWorkerFactory
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: DatabaseWorkerFactory

    @Inject
    lateinit var workManager: WorkManager

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(
                NetworkType.CONNECTED
            ).build()

        val databaseWorkRequest: PeriodicWorkRequest =
            PeriodicWorkRequestBuilder<DatabaseWorker>(
                15, TimeUnit.MINUTES,
                5, TimeUnit.MINUTES
            ).setConstraints(constraints)
                .build()

        workManager
            .enqueueUniquePeriodicWork(
                "databaseWorkRequest",
                ExistingPeriodicWorkPolicy.KEEP,
                databaseWorkRequest
            )
    }
}