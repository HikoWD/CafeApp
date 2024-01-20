package by.ivan.CafeApp.presentation.search_screen.top_bar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import by.ivan.CafeApp.presentation.search_screen.SearchItemsScreenViewModel
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

@Composable
fun SearchTopBar(
    viewModel: SearchItemsScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    menuItemTitleForSearch: String,
    paddingValuesParent: PaddingValues,
    onClearMenuItemsClick: () -> Unit,
    onGetMenuItemsByTitleUpdate: () -> Unit,
    onUpdateMenuItemTitleInput: (String) -> Unit,
    onClearMenuItemTitleForSearchClick: () -> Unit,
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit,
    onAddSearchHistoryItemEffect: (SearchHistoryItem) -> Unit,
    onPopBackStackClick: () -> Unit,
) {
    SearchTopBar(
        menuItems = menuItems,
        menuItemTitleForSearch = menuItemTitleForSearch,
        paddingValuesParent = paddingValuesParent,
        onClearMenuItemsClick = onClearMenuItemsClick,
        onGetMenuItemsByTitleUpdate = onGetMenuItemsByTitleUpdate,
        onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
        onClearMenuItemTitleForSearchClick = onClearMenuItemTitleForSearchClick,
        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
        onAddSearchHistoryItemEffect = onAddSearchHistoryItemEffect,
        onPopBackStackClick = onPopBackStackClick
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SearchTopBar(
    menuItems: List<MenuItem>,
    menuItemTitleForSearch: String = "",
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onClearMenuItemsClick: () -> Unit = {},
    onGetMenuItemsByTitleUpdate: () -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit = {},
    onClearMenuItemTitleForSearchClick: () -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onAddSearchHistoryItemEffect: (SearchHistoryItem) -> Unit = {},
    onPopBackStackClick: () -> Unit = {}
) {
    var isActive by rememberSaveable { mutableStateOf(false) }
    Box(
        Modifier.fillMaxWidth()
    ) {
        SearchBar(
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            modifier = Modifier
                .align(Alignment.TopCenter),
            query = menuItemTitleForSearch,
            onQueryChange = {
                onUpdateMenuItemTitleInput(it)
                onGetMenuItemsByTitleUpdate()
            },
            onSearch = { isActive = false },
            active = isActive,
            onActiveChange = { activeChange ->
                isActive = activeChange
            },
            placeholder = { Text("Введите название блюда") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = {
                IconButton(onClick = {
                    onClearMenuItemTitleForSearchClick()
                    onClearMenuItemsClick()
                }) {
                    Icon(Icons.Default.Clear, contentDescription = null)
                }
            },
            content = {
                LaunchedEffect(key1 = menuItemTitleForSearch) {
                    if (menuItemTitleForSearch.isBlank()) return@LaunchedEffect
                    delay(2000)
                    onAddSearchHistoryItemEffect(
                        SearchHistoryItem(
                            id = 0,
                            query = menuItemTitleForSearch,
                            timestamp = DateTimeFormatter
                                .ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
                                .withZone(ZoneOffset.UTC)
                                .format(Instant.now())
                        )
                    )
                }
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 420.dp),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White)
                        .padding(bottom = paddingValuesParent.calculateBottomPadding())
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
        )
//    TopAppBar(backgroundColor = Color.White) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.SpaceBetween,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(onClick = {
//                onPopBackStackClick()
//            }) {
//                Icon(
//                    Icons.Filled.ArrowBack,
//                    contentDescription = "Back",
//                    tint = Color.Black,
//                )
//            }
//            TextField(
//                value = menuItemTitleForSearch,
//                onValueChange = { value ->
//                    onUpdateMenuItemTitleInput(value)
//                    onGetMenuItemsByTitleUpdate()
//                },
//                modifier = Modifier.fillMaxWidth(),
//                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
//                placeholder = { Text(text = "Введите название блюда") },
//                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
//            )
//        }
//    }
    }
}