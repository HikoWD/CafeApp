package by.ivan.CafeApp.ui.domain.usecase

import android.content.Context
import by.ivan.CafeApp.ui.data.repo.ConnectionStateRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@ViewModelScoped
class CheckInternetConnectionUseCase @Inject constructor(
    private val connectionStateRepository: ConnectionStateRepository,
    @ApplicationContext private val context: Context
) {
    fun isConnect(context: Context = this.context): Boolean {
        return connectionStateRepository.checkForInternet(context)
    }
}