package by.ivan.CafeApp.presentation.order_details_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.order_details_screen.OrderDetailsScreenViewModel

@Composable
fun OrderDetailsTopBar(
    viewModel: OrderDetailsScreenViewModel = hiltViewModel(),
    orderId: Int?,
    onNavigateToHistoryOrdersScreenClick: () -> Unit
) {
    OrderDetailsTopBar(
        orderId = orderId,
        onNavigateToHistoryOrdersScreenClick = onNavigateToHistoryOrdersScreenClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun OrderDetailsTopBar(
    orderId: Int? = -1,
    onNavigateToHistoryOrdersScreenClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = {
                onNavigateToHistoryOrdersScreenClick()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                )
            }
        },
        title = {
            orderId?.let {
                Text(
                    text = "Заказ №$orderId",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
            }
        }
    )
}