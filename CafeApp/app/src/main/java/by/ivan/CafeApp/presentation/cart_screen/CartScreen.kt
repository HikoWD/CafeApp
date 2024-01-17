package by.ivan.CafeApp.presentation.cart_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
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
    navigator: DestinationsNavigator,
    paddingValuesParent: PaddingValues,
//    onGetCartItemsEffect: () -> Unit,
//    onAddMenuItemToCartClick: (MenuItem) -> Unit,
//    onRemoveMenuItemFromCartClick: (MenuItem) -> Unit,
//    onRemoveAllMenuItemsFromCartClick: () -> Unit,
//    onPostOrderClick: () -> Unit,
) {
    CartScreen(
        viewModel = viewModel,
        onNavigateOrderScreenSuccessScreen = { navigator.navigate(OrderSuccessScreenDestination(it)) },
        paddingValuesParent = paddingValuesParent,
        onGetCartItemsEffect = { viewModel.getCartItems() },
        onAddMenuItemToCartClick = { viewModel.addCartItem(menuItem = it) },
        onRemoveMenuItemFromCartClick = {
            viewModel.decreaseCountCartItem(
                menuItem = it
            )
        },
        onRemoveAllMenuItemsFromCartClick = { viewModel.removeCartItems() },
        onPostOrderClick = { viewModel.postOrder() }
    )
}

@Composable
private fun CartScreen(
    viewModel: CartScreenViewModel,
    onNavigateOrderScreenSuccessScreen: (Order) -> Unit = {},
    paddingValuesParent: PaddingValues,
    onGetCartItemsEffect: () -> Unit = {},
    onAddMenuItemToCartClick: (MenuItem) -> Unit = {},
    onRemoveMenuItemFromCartClick: (MenuItem) -> Unit = {},
    onRemoveAllMenuItemsFromCartClick: () -> Unit = {},
    onPostOrderClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CartTopBar(
                viewModel = viewModel,
                onRemoveAllMenuItemsFromCartClick = onRemoveAllMenuItemsFromCartClick
            )
        }) {
        CartMain(
            viewModel = viewModel,
            onNavigateOrderScreenSuccessScreen = onNavigateOrderScreenSuccessScreen,
            onGetCartItemsEffect = onGetCartItemsEffect,
            onAddMenuItemToCartClick = onAddMenuItemToCartClick,
            onRemoveMenuItemFromCartClick = onRemoveMenuItemFromCartClick,
            onPostOrderClick = onPostOrderClick,
            paddingValuesParent = paddingValuesParent,
            paddingValuesChild = it
        )
    }
}