package by.ivan.CafeApp.presentation.history_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.history_screen.HistoryOrdersScreenViewModel

@Composable
fun HistoryOrdersTopBar(
    viewModel: HistoryOrdersScreenViewModel = hiltViewModel()
) {
    HistoryOrdersTopBar()
}

@Composable
private fun HistoryOrdersTopBar() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = "История заказов") }
    )
}