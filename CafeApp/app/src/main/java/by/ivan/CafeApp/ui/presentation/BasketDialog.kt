package by.ivan.CafeApp.ui.presentation

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.graphics.toColor
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.ui.data.models.*
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment
import java.util.*

@Composable
fun BasketDialog(
    viewModel: MainActivityViewModel = hiltViewModel(),
    dialogState: MutableState<Boolean>,
    basketState: MutableList<MenuItem>,
    x: Int = 0, //????? - xdd
    currentTable: MutableState<Table>,
    postMenuItems: (OrderParams) -> by.ivan.CafeApp.ui.data.models.Order
) {
    val state = viewModel.uiState.collectAsState()
    BasketDialog(
        dialogState = dialogState,
        viewModel = viewModel,
        basketState = basketState,
        postMenuItems = postMenuItems,
        currentTable = currentTable
    )
}

@Composable
private fun BasketDialog(
    viewModel: MainActivityViewModel,
    dialogState: MutableState<Boolean> = mutableStateOf(false),
    basketState: MutableList<MenuItem> = mutableListOf(),
    currentTable: MutableState<Table> = mutableStateOf(Table()),
    postMenuItems: (OrderParams) -> by.ivan.CafeApp.ui.data.models.Order = {
        by.ivan.CafeApp.ui.data.models.Order(
            -1, OrderDetails(), -1, ""
        )
    },
) {
    val context = LocalContext.current

    val totalPriceState = remember {
        mutableStateOf(0.0)
    }
    totalPriceState.value = basketState.sumOf {
        it.price
    }

    val itemsState = basketState.distinct().toMutableStateList()

    val categories = remember {
        mutableStateListOf<Category>()
    }

    itemsState.forEach {
        viewModel.getCategoryById(id = it.categoryId, categories = categories)
    }

    Dialog(
        onDismissRequest = { dialogState.value }
    ) {
        Card(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            shape = RoundedCornerShape(5.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Spacer(modifier = Modifier.weight(0.5f))
                Box(
                    modifier = Modifier
                        .weight(1.5f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Ваш заказ", fontSize = 30.sp, color = Color.Black)
                }
                Box(
                    modifier = Modifier.weight(0.5f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    IconButton(onClick = {
                        dialogState.value = false
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_close_24),
                            contentDescription = "Close dialog",
                            tint = Color.Black
                        )
                    }
                }
            }
            LazyColumn(modifier = Modifier.weight(8.5f)) {
                itemsIndexed(items = itemsState) { index, item ->
                    var count: Int = 0;
                    for (i in basketState) {
                        if (item.id == i.id) {
                            count++
                        }
                    }
                    val countItems = remember {
                        mutableStateOf(count)
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Gray),
                        contentAlignment = Alignment.Center
                    ) {
                        categories.forEach {
                            if (it.id == item.categoryId) {
                                Text(text = it.title, color = Color.Black, fontSize = 22.sp)
                            }
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(4.5f)) {
                            Text(text = item.title, fontSize = 18.sp)
                        }
                        Box(modifier = Modifier.weight(1.5f), contentAlignment = Alignment.Center) {
                            Text(
                                text = item.price.toString(),
                                fontSize = 16.sp,
                                modifier = Modifier.padding(top = 15.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(4f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center
                            ) {
                                IconButton(onClick = {
                                    countItems.value--
                                    basketState.remove(item)
                                    totalPriceState.value -= item.price
                                    if (countItems.value == 0) {
                                        itemsState.remove(item)
                                    }
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_remove_24),
                                        contentDescription = "Minus item",
                                        tint = Color.Black
                                    )
                                }
                                Box(contentAlignment = Alignment.Center) {
                                    Text(
                                        text = "${countItems.value}",
                                        fontSize = 16.sp,
                                        modifier = Modifier.padding(top = 12.dp)
                                    )
                                }
                                IconButton(onClick = {
                                    countItems.value++
                                    basketState.add(item)
                                    totalPriceState.value += item.price
                                }) {
                                    Icon(
                                        painter = painterResource(id = R.drawable.baseline_add_24),
                                        contentDescription = "Plus item",
                                        tint = Color.Black
                                    )
                                }
                            }
                        }
                    }
                }
            }
            Column(
                modifier = Modifier
                    .weight(1.5f)
                    .padding(10.dp)
            ) {
                Divider(
                    color = Color.Black,
                    modifier = Modifier
                        .fillMaxWidth()
                        .width(1.dp)
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Text(
                        text = "Итого: ${totalPriceState.value}",
                        fontSize = 26.sp,
                        modifier = Modifier.padding(end = 5.dp)
                    )
                    Divider(
                        color = Color.Black,
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp)
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(modifier = Modifier.weight(2f), contentAlignment = Alignment.CenterStart) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                        ) {
                            IconButton(modifier = Modifier.fillMaxSize(),
                                onClick = {
                                    basketState.clear()
                                    totalPriceState.value = 0.0
                                    //onRemoveAllMenuItemsFromBasketClick()
                                }
                            ) {
                                Icon(
                                    modifier = Modifier.fillMaxSize(),
                                    painter = painterResource(id = R.drawable.baseline_delete_24),
                                    contentDescription = "Remove all items"
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(7f), contentAlignment = Alignment.CenterEnd) {
                        val buttonState = remember {
                            mutableStateOf(false)
                        }
                        buttonState.value = basketState.size != 0
                        Button(
                            modifier = Modifier.fillMaxSize(),
                            enabled = buttonState.value,
                            onClick = {
                                if (viewModel.isConnect()) {
                                    val orderDetails = OrderDetails()
                                    orderDetails.menuItemsIdsText =
                                        basketState.joinToString(separator = ";") {
                                            it.id.toString()
                                        }
                                    val orderParams: OrderParams =
                                        OrderParams(orderDetails = orderDetails, currentTable.value)
                                    postMenuItems(orderParams)
                                    basketState.clear()
                                    dialogState.value = false
                                } else {
                                    Toast.makeText(
                                        context,
                                        "Отсутствует подключение к интернету!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        ) {
                            Text(text = "Потвердить заказ", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    }
}