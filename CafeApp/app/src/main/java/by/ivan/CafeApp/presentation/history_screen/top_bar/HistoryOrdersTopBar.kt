package by.ivan.CafeApp.presentation.history_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.presentation.history_screen.HistoryOrdersScreenViewModel

@Composable
fun HistoryOrdersTopBar(
    viewModel: HistoryOrdersScreenViewModel = hiltViewModel(),
    onMenuButtonClick: () -> Unit
) {
    HistoryOrdersTopBar(onMenuButtonClick = onMenuButtonClick)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryOrdersTopBar(onMenuButtonClick: () -> Unit = {}) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = {
                onMenuButtonClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = stringResource(id = R.string.drawer_menu_description)
                )
            }
        },
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