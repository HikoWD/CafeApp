package by.ivan.CafeApp.presentation.cart_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.presentation.CartNavGraph
import by.ivan.CafeApp.presentation.cart_screen.main.CartMain
import by.ivan.CafeApp.presentation.cart_screen.top_bar.CartTopBar
import by.ivan.CafeApp.presentation.destinations.OrderSuccessScreenDestination
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@CartNavGraph(start = true)
@Destination
@Composable
fun CartScreen(
    viewModel: CartScreenViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigator: DestinationsNavigator,
    paddingValuesParent: PaddingValues,
    onMenuButtonClick: () -> Unit
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    CartScreen(
        viewModel = viewModel,
        cartItems = state.cartItems,
        isNotEmptyUiState = state.cartItems.isNotEmpty(),
        orderResult = state.orderResult,
        errorResult = state.errorResult,
        orderPostState = state.orderPostState,
        onNavigateOrderScreenSuccessScreen = { navigator.navigate(OrderSuccessScreenDestination(it)) },
        paddingValuesParent = paddingValuesParent,
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(menuItem = it) },
        onRemoveMenuItemFromCartClick = {
            viewModel.decreaseCountCartItem(
                menuItem = it
            )
        },
        onRemoveAllMenuItemsFromCartClick = { viewModel.removeCartItems() },
        onPostOrderClick = { viewModel.postOrder() },
        onChangeStateToIdleEffect = { viewModel.changeStateToIdle() },
        onMenuButtonClick = onMenuButtonClick
    )
}

@Composable
private fun CartScreen(
    viewModel: CartScreenViewModel,
    cartItems: List<CartItem> = listOf(),
    isNotEmptyUiState: Boolean = false,
    orderResult: Order = Order(),
    errorResult: String = "",
    orderPostState: OrderPostState = OrderPostState.IDLE,
    onNavigateOrderScreenSuccessScreen: (Order) -> Unit = {},
    paddingValuesParent: PaddingValues,
    onAddMenuItemToCartClick: (MenuItem) -> Unit = {},
    onRemoveMenuItemFromCartClick: (MenuItem) -> Unit = {},
    onRemoveAllMenuItemsFromCartClick: () -> Unit = {},
    onPostOrderClick: () -> Unit = {},
    onChangeStateToIdleEffect: () -> Unit = {},
    onMenuButtonClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CartTopBar(
                viewModel = viewModel,
                onRemoveAllMenuItemsFromCartClick = onRemoveAllMenuItemsFromCartClick,
                onMenuButtonClick = onMenuButtonClick
            )
        },
        content = { paddingValuesChild ->
            CartMain(
                viewModel = viewModel,
                cartItems = cartItems,
                isNotEmptyUiState = isNotEmptyUiState,
                orderResult = orderResult,
                errorResult = errorResult,
                orderPostState = orderPostState,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild,
                onNavigateOrderScreenSuccessScreen = onNavigateOrderScreenSuccessScreen,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                onRemoveMenuItemFromCartClick = onRemoveMenuItemFromCartClick,
                onPostOrderClick = onPostOrderClick,
                onChangeStateToIdleEffect = onChangeStateToIdleEffect,
            )
        }
    )
}