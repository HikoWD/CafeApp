package by.ivan.CafeApp.presentation.menu_screen.main

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.category.model.Category
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.menu_screen.CategoriesScreenState
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenState
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel
import coil.compose.AsyncImage

@Composable
fun MenuItemsMain(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    categories: List<Category>,
    menuItems: List<MenuItem>,
    categoriesScreenState: CategoriesScreenState,
    menuItemsScreenState: MenuItemsScreenState,
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit,
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues
) {
    MenuItemsMain(
        categories = categories,
        menuItems = menuItems,
        categoriesScreenState = categoriesScreenState,
        menuItemsScreenState = menuItemsScreenState,
        onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick,
        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
        paddingValuesParent = paddingValuesParent,
        paddingValuesChild = paddingValuesChild
    )
}

@Composable
private fun MenuItemsMain(
    categories: List<Category> = listOf(),
    menuItems: List<MenuItem> = listOf(),
    categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    menuItemsScreenState: MenuItemsScreenState = MenuItemsScreenState.Idle,
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    paddingValuesChild: PaddingValues = PaddingValues(2.dp)
) {
    val localContext = LocalContext.current

    Column(modifier = Modifier.fillMaxSize()) {
        Crossfade(
            targetState = categoriesScreenState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            animationSpec = tween(
                durationMillis = 800,
                easing = LinearEasing
            ),
            label = ""
        ) { state ->
            when (state) {
                is CategoriesScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is CategoriesScreenState.Error -> {
                    Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG).show()
                    DrawCategories(
                        categories = categories,
                        onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick
                    )
                }

                is CategoriesScreenState.Loaded -> {
                    DrawCategories(
                        categories = categories,
                        onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick
                    )
                }

                is CategoriesScreenState.Idle -> {}

                else -> {}
            }
        }

        Crossfade(
            targetState = menuItemsScreenState,
            modifier = Modifier
                .weight(9f)
                .fillMaxWidth(),
            animationSpec = tween(
                durationMillis = 400,
                //delayMillis = 100, //todo
                easing = FastOutSlowInEasing
            ),
            label = ""
        ) { state ->
            when (state) {
                is MenuItemsScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is MenuItemsScreenState.Error -> {
                    Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG).show()
                    DrawMenuItems(
                        menuItems = menuItems,
                        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                        padding = paddingValuesChild
                    )
                }

                is MenuItemsScreenState.Loaded -> {
                    DrawMenuItems(
                        menuItems = menuItems,
                        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                        padding = paddingValuesChild
                    )
                }

                is MenuItemsScreenState.Idle -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White)
                    )
                }

                else -> {}
            }
        }
    }
}

@Composable
private fun DrawCategories(
    categories: List<Category>,
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
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
                        onGetMenuItemsByCategoryIdClick(item.id)
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
}

@Composable
private fun DrawMenuItems(
    menuItems: List<MenuItem>,
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit,
    padding: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 420.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues = padding)
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
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = item.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    AsyncImage(
                        model = "${by.ivan.CafeApp.data.Constants.URL}/${item.image}",
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
                                onAddMenuItemToCartClick(item)
                            }) {
                            Text(
                                text = "В корзину",
                                fontSize = 16.sp,
                                color = Color.Black,
                            )
                        }
                    }
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
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left
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