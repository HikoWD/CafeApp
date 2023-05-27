package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.ivan.CafeApp.R
import by.ivan.CafeApp.ui.data.Constants
import by.ivan.CafeApp.ui.data.models.Category
import by.ivan.CafeApp.ui.data.models.MenuItem
import coil.compose.AsyncImage

@Composable
fun MenuItemsScreen(
    viewModel: MainActivityViewModel = hiltViewModel(),
    basketState: MutableList<MenuItem>,
    navController: NavController,
    onGetMenuItemsByCategoryClick: (Int) -> Unit,
    getMenuItemsSortedByAlphabet: (Int) -> Unit,
    getMenuItemsSortedByPrice: (Int) -> Unit,
    currentCategory: MutableState<Category>,
    padding: PaddingValues
) {
    val state = viewModel.uiState.collectAsState()
    MenuItemsScreen(
        viewModel = viewModel,
        menuItems = state.value.menuItems,
        categories = state.value.categories,
        navController = navController,
        basketState = basketState,
        onGetMenuItemsByCategoryClick = onGetMenuItemsByCategoryClick,
        getMenuItemsSortedByAlphabet = getMenuItemsSortedByAlphabet,
        getMenuItemsSortedByPrice = getMenuItemsSortedByPrice,
        currentCategory = currentCategory,
        padding = padding
    )
}

@Composable
private fun MenuItemsScreen(
    menuItems: List<MenuItem>,
    categories: List<Category>,
    viewModel: MainActivityViewModel,
    basketState: MutableList<MenuItem> = mutableListOf(),
    onAddMenuItemToBasket: (MenuItem) -> Unit = {},
    onGetMenuItemsByCategoryClick: (Int) -> Unit = {},
    getMenuItemsSortedByAlphabet: (Int) -> Unit = {},
    getMenuItemsSortedByPrice: (Int) -> Unit = {},
    navController: NavController,
    currentCategory: MutableState<Category> = mutableStateOf(Category()),
    padding: PaddingValues
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()){
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(7f)
                    .padding(),
            ) {
                itemsIndexed(items = categories) { index, item ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Button(modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                            border = BorderStroke(1.dp, Color.Black),
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                            onClick = {
                                onGetMenuItemsByCategoryClick(item.id)
                                currentCategory.value = item
                            }) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = item.title, fontSize = 20.sp)
                            }
                        }
                    }
                }
            }
            Box(modifier = Modifier.weight(3f), contentAlignment = Alignment.CenterEnd) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
                    IconButton(onClick = {
                        getMenuItemsSortedByAlphabet(menuItems[0].categoryId)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_sort_by_alpha_24),
                            contentDescription = "Sort by alphabet",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {
                        getMenuItemsSortedByPrice(menuItems[0].categoryId)
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.baseline_attach_money_24),
                            contentDescription = "Sort by price",
                            tint = Color.Black
                        )
                    }
                }
            }
        }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 420.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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
                            Text(
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
                            Text(
                                text = "${item.price} р",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Black
                            )
                            Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                                onClick = {
                                    basketState.add(item)
                                }) {
                                Box(
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = "В корзину", fontSize = 16.sp, color = Color.Black)
                                }
                            }
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
                            Text(
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
                            Text(
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