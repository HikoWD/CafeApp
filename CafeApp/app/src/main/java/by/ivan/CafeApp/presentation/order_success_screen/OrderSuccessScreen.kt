package by.ivan.CafeApp.presentation.order_success_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.order.model.Order
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
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigator: DestinationsNavigator,
    navArgs: OrderSuccessScreenNavArgs,
    paddingValuesParent: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                navArgs.order?.let { order ->
                    viewModel.getMenuItemsByOrderItemsId(
                        order = order
                    )
                }
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    OrderSuccessScreen(
        viewModel = viewModel,
        menuItems = state.menuItems,
        order = navArgs.order,
        orderSuccessScreenState = state.orderSuccessScreenState,
        paddingValuesParent = paddingValuesParent,
        onNavigateToMenuItemsScreenClick = { navigator.navigate(NavGraphs.menu) }
    )
}

@Composable
private fun OrderSuccessScreen(
    viewModel: OrderSuccessScreenViewModel,
    menuItems: List<MenuItem> = listOf(),
    order: Order? = Order(),
    orderSuccessScreenState: OrderSuccessScreenState = OrderSuccessScreenState.Idle,
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onNavigateToMenuItemsScreenClick: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { OrderSuccessScreenTopBar(viewModel = viewModel, order = order) },
        content = { paddingValuesChild ->
            OrderSuccessScreenMain(
                viewModel = viewModel,
                menuItems = menuItems,
                orderSuccessScreenState = orderSuccessScreenState,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild,
                onNavigateToMenuItemsScreenClick = onNavigateToMenuItemsScreenClick,
            )
        }
    )
}