package by.ivan.CafeApp.presentation.order_details_screen

import by.ivan.CafeApp.domain.menu.model.MenuItem

data class OrderDetailsScreenUiState(
    val menuItems: List<MenuItem> = listOf(),
    val orderDetailsScreenState: OrderDetailsScreenState = OrderDetailsScreenState.Idle,
)

sealed class OrderDetailsScreenState {
    object Loading : OrderDetailsScreenState()
    object Loaded : OrderDetailsScreenState()
    object Empty : OrderDetailsScreenState()
    object Idle : OrderDetailsScreenState()
}
