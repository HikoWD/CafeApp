package by.ivan.CafeApp.presentation.history_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
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
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigator: DestinationsNavigator,
    paddingValuesParent: PaddingValues,
    onMenuButtonClick: () -> Unit
) {
    HistoryOrdersScreen(
        viewModel = viewModel,
        paddingValuesParent = paddingValuesParent,
        onNavigateToOrderDetailsScreenClick = {
            navigator.navigate(
                OrderDetailsScreenDestination(it)
            )
        },
        onMenuButtonClick = onMenuButtonClick
    )
}

@Composable
private fun HistoryOrdersScreen(
    viewModel: HistoryOrdersScreenViewModel,
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onNavigateToOrderDetailsScreenClick: (Order) -> Unit = {},
    onMenuButtonClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            HistoryOrdersTopBar(
                viewModel = viewModel,
                onMenuButtonClick = onMenuButtonClick
            )
        },
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