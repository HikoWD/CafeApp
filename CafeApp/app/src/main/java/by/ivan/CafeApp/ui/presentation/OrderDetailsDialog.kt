package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.ui.data.Constants
import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.models.OrderDetails
import coil.compose.AsyncImage
import java.util.*

@Composable
fun OrderDetailsDialog(
    viewModel: MainActivityViewModel = hiltViewModel(),
    orderDetailsDialogState: MutableState<Boolean>,
    order: by.ivan.CafeApp.ui.data.models.Order,
    x: Int = 0 //????? - xdd
) {
    OrderDetailsDialog(
        viewModel = viewModel,
        orderDetailsDialogState = orderDetailsDialogState,
        order = order
    )
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OrderDetailsDialog(
    viewModel: MainActivityViewModel,
    orderDetailsDialogState: MutableState<Boolean> = mutableStateOf(false),
    order: by.ivan.CafeApp.ui.data.models.Order = by.ivan.CafeApp.ui.data.models.Order(
        -1, OrderDetails(), -1,
        ""
    ),
) {
    Dialog(
        onDismissRequest = { orderDetailsDialogState.value = false },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Card(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(0.5f))
                    Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.TopCenter) {
                        Text(text = "Заказ №${order.id}", fontSize = 26.sp, fontWeight = FontWeight.Black)
                    }
                    Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.CenterEnd) {
                        IconButton(onClick = {
                            orderDetailsDialogState.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
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
                val menuItems = remember {
                    mutableStateListOf<MenuItem>()
                }

                menuItems.clear()
                val menuItemIds = order.orderDetails.menuItemsIdsText.split(';')
                menuItemIds.forEach {
                    viewModel.getMenuItemById(it.toInt(), menuItems)
                }
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 250.dp)) {
                    itemsIndexed(items = menuItems) { index, item ->
                        Card(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 20.dp, vertical = 5.dp)
                                .shadow(
                                    elevation = 5.dp,
                                    clip = true,
                                    shape = RoundedCornerShape(5.dp)
                                ),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.background,
                            ),
                        ) {
                            Column(
                                modifier = Modifier,
                                verticalArrangement = Arrangement.SpaceBetween,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    contentAlignment = Alignment.Center
                                ) {
                                    androidx.compose.material.Text(
                                        text = item.title,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }

                                AsyncImage(
                                    model = "${Constants.URL}/${item.image}",
                                    contentDescription = "product picture",
                                    modifier = Modifier.fillMaxSize()
                                )
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 2.dp,
                                            top = 4.dp,
                                            end = 2.dp,
                                            bottom = 4.dp
                                        ),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    androidx.compose.material.Text(
                                        text = item.price.toString(),
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 2.dp,
                                            top = 4.dp,
                                            end = 2.dp,
                                            bottom = 4.dp
                                        )
                                ) {
                                    androidx.compose.material.Text(
                                        text = "${item.weight} кг",
                                        fontSize = 16.sp
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 2.dp,
                                            top = 4.dp,
                                            end = 2.dp,
                                            bottom = 4.dp
                                        ),
                                    contentAlignment = Alignment.CenterStart
                                ) {
                                    androidx.compose.material.Text(
                                        text = item.description,
                                        textAlign = TextAlign.Left,
                                        fontSize = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}