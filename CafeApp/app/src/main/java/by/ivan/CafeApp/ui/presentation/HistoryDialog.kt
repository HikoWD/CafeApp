package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.ui.data.models.*
import java.util.*

@Composable
fun HistoryDialog(
    viewModel: MainActivityViewModel = hiltViewModel(),
    historyState: MutableState<Boolean>,
    currentCategory: MutableState<Category>
) {
    val state = viewModel.uiState.collectAsState()
    HistoryDialog(
        viewModel = viewModel,
        historyState = historyState,
        orders = state.value.orders,
        currentCategory = currentCategory
    )
}

@Composable
private fun HistoryDialog(
    viewModel: MainActivityViewModel,
    historyState: MutableState<Boolean> = mutableStateOf(false),
    orders: List<by.ivan.CafeApp.ui.data.models.Order>,
    currentCategory: MutableState<Category> = mutableStateOf(Category()),
) {
    Dialog(
        onDismissRequest = { historyState.value },
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.weight(0.5f))
                Box(
                    modifier = Modifier.weight(3f),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Text(text = "История заказов", fontSize = 26.sp, fontWeight = FontWeight.Black)
                }
                Box(
                    modifier = Modifier.weight(0.5f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(onClick = {
                        viewModel.getMenuItemsByCategory(currentCategory.value.id)
                        historyState.value = false
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Close dialog",
                            tint = Color.Black
                        )
                    }
                }
            }
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp)
            )
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 250.dp),
                modifier = Modifier.fillMaxSize(),
            ) {
                itemsIndexed(items = orders) { index, item ->
                    val orderDetailsDialogState = remember {
                        mutableStateOf(false)
                    }

                    if (orderDetailsDialogState.value) {
                        OrderDetailsDialog(
                            viewModel = viewModel,
                            order = item,
                            orderDetailsDialogState = orderDetailsDialogState
                        )
                    }
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
                                orderDetailsDialogState.value = true
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
    }
}