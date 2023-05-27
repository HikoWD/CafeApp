package by.ivan.CafeApp.ui.data.local.di

import android.content.Context
import androidx.work.WorkManager
import by.ivan.CafeApp.ui.data.local.DataStore.DataStoreTable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WorkerModule {
    @Provides
    @Singleton
    fun provideWorkManager(@ApplicationContext appContext: Context): WorkManager {
        return WorkManager.getInstance(appContext)
    }
}