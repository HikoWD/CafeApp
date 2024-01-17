package by.ivan.CafeApp.presentation.order_success_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.OrderDetails
import by.ivan.CafeApp.presentation.CartNavGraph
import by.ivan.CafeApp.presentation.NavGraphs
import by.ivan.CafeApp.presentation.order_success_screen.args.OrderSuccessScreenNavArgs
import by.ivan.CafeApp.presentation.order_success_screen.main.OrderSuccessScreenMain
import by.ivan.CafeApp.presentation.order_success_screen.top_bar.OrderSuccessScreenTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@CartNavGraph
@Destination(navArgsDelegate = OrderSuccessScreenNavArgs::class)
@Composable
fun OrderSuccessScreen(
    viewModel: OrderSuccessScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    navArgs: OrderSuccessScreenNavArgs,
    paddingValuesParent: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getMenuItemsByOrderItemsIds(order = navArgs.order)
    }

    OrderSuccessScreen(
        viewModel = viewModel,
        menuItems = state.menuItems,
        order = navArgs.order,
        paddingValuesParent = paddingValuesParent,
        onNavigateToMenuClick = {
            navigator.navigate(NavGraphs.menu)
        }
    )
}

@Composable
private fun OrderSuccessScreen(
    viewModel: OrderSuccessScreenViewModel,
    menuItems: List<MenuItem> = listOf(),
    order: Order = Order(
        id = 1,
        orderDetails = OrderDetails(id = 1, menuItemsIdsText = ""),
        tableId = 1,
        timestamp = ""
    ),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onNavigateToMenuClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { OrderSuccessScreenTopBar(viewModel = viewModel, orderId = order.id) },
        content = { paddingValuesChild ->
            OrderSuccessScreenMain(
                viewModel = viewModel,
                menuItems = menuItems,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild,
                onNavigateToMenuClick = onNavigateToMenuClick,
            )
        }
    )
}