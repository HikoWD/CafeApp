package by.ivan.CafeApp.presentation.history_screen.main

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.presentation.history_screen.HistoryOrdersScreenState
import by.ivan.CafeApp.presentation.history_screen.HistoryOrdersScreenViewModel

@Composable
fun HistoryOrdersMain(
    viewModel: HistoryOrdersScreenViewModel = hiltViewModel(),
    onNavigateToOrderDetailsScreenClick: (Order) -> Unit
) {
    val state = viewModel.uiState.collectAsState()

    HistoryOrdersMain(
        orders = state.value.orders,
        historyOrdersScreenState = state.value.historyOrdersScreenState,
        onNavigateToOrderDetailsScreenClick = onNavigateToOrderDetailsScreenClick,
    )
}

@Composable
private fun HistoryOrdersMain(
    orders: List<Order>,
    historyOrdersScreenState: HistoryOrdersScreenState = HistoryOrdersScreenState.Nothing,
    onNavigateToOrderDetailsScreenClick: (Order) -> Unit = {}
) {
    val localContext = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Crossfade(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            targetState = historyOrdersScreenState,
            animationSpec = tween(
                durationMillis = 800,
                easing = LinearEasing
            ),
            label = ""
        ) { state ->
            when (state) {
                is HistoryOrdersScreenState.Error -> {
                    Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG).show()
                }

                HistoryOrdersScreenState.Loaded -> {
                    LazyVerticalGrid(
                        columns = GridCells.Adaptive(minSize = 250.dp),
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        itemsIndexed(items = orders) { index, item ->
                            Card(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(horizontal = 20.dp, vertical = 5.dp)
                                    .shadow(
                                        elevation = 5.dp,
                                        clip = true,
                                        shape = RoundedCornerShape(5.dp)
                                    )
                                    .clickable {
                                        onNavigateToOrderDetailsScreenClick(item)
                                    }
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxSize(),
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Заказ №: ${item.id}",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Text(text = item.timestamp, fontSize = 20.sp)
                                }
                            }
                        }
                    }
                }
                HistoryOrdersScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
                HistoryOrdersScreenState.Nothing -> {}
            }
        }
    }
}