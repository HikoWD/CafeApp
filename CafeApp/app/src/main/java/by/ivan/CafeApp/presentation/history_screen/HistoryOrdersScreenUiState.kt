package by.ivan.CafeApp.presentation.history_screen

import by.ivan.CafeApp.domain.order.model.Order

data class HistoryOrdersScreenUiState(
    val orders: List<Order> = listOf(),
    val historyOrdersScreenState: HistoryOrdersScreenState = HistoryOrdersScreenState.Nothing
)

sealed class HistoryOrdersScreenState {
    object Loading : HistoryOrdersScreenState()
    object Loaded : HistoryOrdersScreenState()
    data class Error(val errorMessage: String?) : HistoryOrdersScreenState()
    object Nothing : HistoryOrdersScreenState()
}