package by.ivan.CafeApp.domain.result

sealed class CompletableResult {
    object Success : CompletableResult()
    data class Error(val errorMessage: String?) : CompletableResult()
}