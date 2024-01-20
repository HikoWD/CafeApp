package by.ivan.CafeApp.presentation.history_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.presentation.HistoryNavGraph
import by.ivan.CafeApp.presentation.destinations.OrderDetailsScreenDestination
import by.ivan.CafeApp.presentation.history_screen.main.HistoryOrdersMain
import by.ivan.CafeApp.presentation.history_screen.top_bar.HistoryOrdersTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@HistoryNavGraph(start = true)
@Destination
@Composable
fun HistoryOrdersScreen(
    viewModel: HistoryOrdersScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    paddingValuesParent: PaddingValues
) {
    DisposableEffect(Unit) {
        val job = viewModel.getOrders()

        onDispose {
            job.cancel()
        }
    }

    SideEffect {
        viewModel.searchNewOrder()
    }

    HistoryOrdersScreen(
        viewModel = viewModel,
        paddingValuesParent = paddingValuesParent,
        onNavigateToOrderDetailsScreenClick = {
            navigator.navigate(
                OrderDetailsScreenDestination(it)
            )
        },
    )
}

@Composable
private fun HistoryOrdersScreen(
    viewModel: HistoryOrdersScreenViewModel,
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onNavigateToOrderDetailsScreenClick: (Order) -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = { HistoryOrdersTopBar(viewModel = viewModel) },
        content = { paddingValuesChild ->
            HistoryOrdersMain(
                viewModel = viewModel,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild,
                onNavigateToOrderDetailsScreenClick = onNavigateToOrderDetailsScreenClick,
            )
        }
    )
}