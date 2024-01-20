package by.ivan.CafeApp.presentation.history_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryOrdersTopBar() {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "История заказов",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    )
}