package by.ivan.CafeApp.presentation.order_details_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
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

@Composable
private fun OrderDetailsTopBar(
    orderId: Int? = -1,
    onNavigateToHistoryOrdersScreenClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
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
                    fontSize = 20.sp,
                    textAlign = TextAlign.End
                )
            }
        }
    )
}