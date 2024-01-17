package by.ivan.CafeApp.presentation.cart_screen

import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.order.model.Order

data class CartScreenUiState(
    val cartItems: List<CartItem> = listOf(),
    val orderResult: Order = Order(),
    val errorResult: String = "",
    val orderPostState: OrderPostState = OrderPostState.IDLE,
    val showAnimation: Boolean = false
)

enum class OrderPostState{
    LOADING, ERROR, POSTED, IDLE
}