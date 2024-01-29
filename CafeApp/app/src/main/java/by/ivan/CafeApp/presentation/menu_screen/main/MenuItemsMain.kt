package by.ivan.CafeApp.presentation.menu_screen.main

import android.content.Context
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
    val localContext = LocalContext.current

    MenuItemsMain(
        localContext = localContext,
        categories = categories,
        menuItems = menuItems,
        categoriesScreenState = categoriesScreenState,
        menuItemsScreenState = menuItemsScreenState,
        paddingValuesParent = paddingValuesParent,
        paddingValuesChild = paddingValuesChild,
        onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick,
        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
    )
}

@Composable
private fun MenuItemsMain(
    localContext: Context = LocalContext.current,
    categories: List<Category> = listOf(),
    menuItems: List<MenuItem> = listOf(),
    categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    menuItemsScreenState: MenuItemsScreenState = MenuItemsScreenState.Idle,
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    paddingValuesChild: PaddingValues = PaddingValues(2.dp),
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValuesChild.calculateTopPadding(),
                bottom = paddingValuesParent.calculateBottomPadding()
            )
    ) {
        Crossfade(
            targetState = categoriesScreenState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            animationSpec = tween(
                durationMillis = 400,
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
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is CategoriesScreenState.Error -> {
                    Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG).show()
                    DrawCategories(
                        categories = categories,
                        onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild
                    )
                }

                is CategoriesScreenState.Loaded -> {
                    DrawCategories(
                        categories = categories,
                        onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild
                    )
                }

                is CategoriesScreenState.Idle -> {}

                else -> {}
            }
        }

        Crossfade(
            targetState = menuItemsScreenState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f),
            animationSpec = tween(
                durationMillis = 400,
                easing = FastOutSlowInEasing
            ),
            label = ""
        ) { state ->
            when (state) {
                is MenuItemsScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is MenuItemsScreenState.Error -> {
                    Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG).show()
                    DrawMenuItems(
                        menuItems = menuItems,
                        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild
                    )
                }

                is MenuItemsScreenState.Loaded -> {
                    DrawMenuItems(
                        menuItems = menuItems,
                        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild
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
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        itemsIndexed(items = categories) { index, item ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                OutlinedButton(modifier = Modifier
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
                        Text(text = item.title, fontSize = 20.sp, textAlign = TextAlign.Center)
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
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 420.dp),
        modifier = Modifier
            .fillMaxSize()
    ) {
        itemsIndexed(items = menuItems) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .shadow(
                        elevation = 10.dp,
                        clip = true,
                        shape = RoundedCornerShape(20.dp)
                    ),
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                ),
                border = BorderStroke(1.dp, Color.Black),
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
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
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
                                start = 6.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 6.dp
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${item.price} р",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                        FilledTonalButton(
                            onClick = {
                                onAddMenuItemToCartClick(item)
                            }) {
                            Text(
                                text = "В корзину",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onBackground
                            )
                        }
                    }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 6.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 6.dp
                            ),
                        text = "${item.weight} кг",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Left
                    )

                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 6.dp,
                                top = 4.dp,
                                end = 4.dp,
                                bottom = 6.dp
                            ),
                        text = item.description,
                        textAlign = TextAlign.Left,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }
        }
    }
}