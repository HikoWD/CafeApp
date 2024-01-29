package by.ivan.CafeApp.presentation.order_details_screen

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
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigator: DestinationsNavigator,
    navArgs: OrderDetailsScreenNavArgs,
    paddingValuesParent: PaddingValues,
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

    OrderDetailsScreen(
        viewModel = viewModel,
        menuItems = state.menuItems,
        paddingValuesParent = paddingValuesParent,
        order = navArgs.order,
        onNavigateToHistoryOrdersScreenClick = { navigator.navigateUp() }
    )
}

@Composable
private fun OrderDetailsScreen(
    viewModel: OrderDetailsScreenViewModel,
    menuItems: List<MenuItem> = listOf(),
    order: Order? = Order(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
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
        },
        content = { paddingValuesChild ->
            OrderDetailsMain(
                viewModel = viewModel,
                menuItems = menuItems,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild
            )
        }
    )
}