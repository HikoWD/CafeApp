package by.ivan.CafeApp.presentation.order_details_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.OrderDetails
import by.ivan.CafeApp.presentation.HistoryNavGraph
import by.ivan.CafeApp.presentation.order_details_screen.args.OrderDetailsScreenNavArgs
import by.ivan.CafeApp.presentation.order_details_screen.main.OrderDetailsMain
import by.ivan.CafeApp.presentation.order_details_screen.top_bar.OrderDetailsTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HistoryNavGraph
@Destination(navArgsDelegate = OrderDetailsScreenNavArgs::class)
@Composable
fun OrderDetailsScreen(
    viewModel: OrderDetailsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    navArgs: OrderDetailsScreenNavArgs,
    paddingValuesParent: PaddingValues,
) {
    OrderDetailsScreen(
        viewModel = viewModel,
        navigator = navigator,
        order = navArgs.order,
        onNavigateToHistoryOrdersScreenClick = { navigator.navigateUp() }
    )
}

@Composable
private fun OrderDetailsScreen(
    viewModel: OrderDetailsScreenViewModel,
    navigator: DestinationsNavigator,
    order: Order? = Order(
        id = 1,
        orderDetails = OrderDetails(id = 1, menuItemsIdsText = ""),
        tableId = 1,
        timestamp = ""
    ),
    onNavigateToHistoryOrdersScreenClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            OrderDetailsTopBar(
                viewModel = viewModel,
                orderId = order?.id,
                onNavigateToHistoryOrdersScreenClick = onNavigateToHistoryOrdersScreenClick
            )
        }) { padding ->
        OrderDetailsMain(
            viewModel = viewModel,
            order = order,
            navigator = navigator,
            paddingValuesChild = padding
        )
    }
}