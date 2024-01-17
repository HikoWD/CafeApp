package by.ivan.CafeApp.presentation.order_details_screen.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.presentation.order_details_screen.OrderDetailsScreenViewModel
import coil.compose.AsyncImage
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun OrderDetailsMain(
    viewModel: OrderDetailsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    order: Order?,
    paddingValuesChild: PaddingValues
) {
    val state by viewModel.uiState.collectAsState()

    SideEffect {
        order?.let {
            viewModel.getMenuItemsByOrderItemsIds(order = order)
        }
    }

    OrderDetailsMain(
        viewModel = viewModel,
        navigator = navigator,
        menuItems = state.menuItems
    )
}

@Composable
private fun OrderDetailsMain(
    viewModel: OrderDetailsScreenViewModel,
    navigator: DestinationsNavigator,
    menuItems: List<MenuItem>
) {
    Column(modifier = Modifier.fillMaxSize()) {
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
                        Text(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = item.title,
//                            style = MaterialTheme.typography.titleLarge
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Center
                        )
                        AsyncImage(
                            model = "${by.ivan.CafeApp.data.Constants.URL}/${item.image}",
                            contentDescription = "product picture",
                            modifier = Modifier.fillMaxSize()
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 2.dp,
                                    top = 4.dp,
                                    end = 2.dp,
                                    bottom = 4.dp
                                ),
                            text = item.price.toString(),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black,
                            textAlign = TextAlign.Left,
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 2.dp,
                                    top = 4.dp,
                                    end = 2.dp,
                                    bottom = 4.dp
                                ),
                            text = "${item.weight} кг",
                            textAlign = TextAlign.Left,
                            fontSize = 16.sp
                        )
                        Text(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(
                                    start = 2.dp,
                                    top = 4.dp,
                                    end = 2.dp,
                                    bottom = 4.dp
                                ),
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