package by.ivan.CafeApp.presentation.order_success_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.order_success_screen.OrderSuccessScreenViewModel

@Composable
fun OrderSuccessScreenTopBar(
    viewModel: OrderSuccessScreenViewModel = hiltViewModel(),
    orderId: Int
) {
    OrderSuccessScreenTopBar(orderId = orderId)
}

@Composable
private fun OrderSuccessScreenTopBar(orderId: Int = -1) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(text = "Успешно! Заказ №${orderId}", color = Color.White) }
    )
}