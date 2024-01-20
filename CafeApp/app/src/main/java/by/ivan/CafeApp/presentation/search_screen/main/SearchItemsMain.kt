package by.ivan.CafeApp.presentation.search_screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import by.ivan.CafeApp.presentation.search_screen.SearchItemsScreenViewModel

@Composable
fun SearchItemsMain(
    viewModel: SearchItemsScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    searchHistory: List<SearchHistoryItem>,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
    onGetMenuItemsByTitleUpdate: () -> Unit,
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit,
    onUpdateMenuItemTitleInput: (String) -> Unit,
) {
    SearchItemsMain(
        menuItems = menuItems,
        searchHistory = searchHistory,
        onGetMenuItemsByTitleUpdate = onGetMenuItemsByTitleUpdate,
        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
        onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
        paddingValuesParent = paddingValuesParent,
        paddingValuesChild = paddingValuesChild
    )
}

@Composable
private fun SearchItemsMain(
    menuItems: List<MenuItem> = listOf(),
    searchHistory: List<SearchHistoryItem> = listOf(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    paddingValuesChild: PaddingValues = PaddingValues(2.dp),
    onGetMenuItemsByTitleUpdate: () -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit = {},
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = paddingValuesParent.calculateBottomPadding())
    ) {
        itemsIndexed(items = searchHistory) { index, item ->
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
                        onUpdateMenuItemTitleInput(item.query)
                        onGetMenuItemsByTitleUpdate()
                    },
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 4.dp,
                            top = 4.dp,
                            end = 4.dp,
                            bottom = 4.dp
                        )
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.query,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.timestamp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(minSize = 420.dp),
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(bottom = paddingValuesParent.calculateBottomPadding())
//    ) {
//        itemsIndexed(items = menuItems) { index, menuItem ->
//            Card(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(horizontal = 20.dp, vertical = 5.dp)
//                    .shadow(
//                        elevation = 5.dp,
//                        clip = true,
//                        shape = RoundedCornerShape(5.dp)
//                    ),
//                colors = CardDefaults.cardColors(
//                    containerColor = MaterialTheme.colorScheme.background,
//                ),
//            ) {
//                Column(
//                    modifier = Modifier,
//                    verticalArrangement = Arrangement.SpaceBetween,
//                    horizontalAlignment = Alignment.CenterHorizontally
//                ) {
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth(),
//                        text = menuItem.title,
//                        fontSize = 24.sp,
//                        fontWeight = FontWeight.Black,
//                        textAlign = TextAlign.Center
//                    )
//                    AsyncImage(
//                        model = "${Constants.URL}/${menuItem.image}",
//                        contentDescription = "product picture",
//                        modifier = Modifier.fillMaxSize()
//                    )
//                    Row(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                start = 2.dp,
//                                top = 4.dp,
//                                end = 2.dp,
//                                bottom = 4.dp
//                            ),
//                        horizontalArrangement = Arrangement.SpaceBetween,
//                        verticalAlignment = Alignment.CenterVertically
//                    ) {
//                        Text(
//                            text = "${menuItem.price} р",
//                            fontSize = 20.sp,
//                            fontWeight = FontWeight.Black
//                        )
//                        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
//                            onClick = {
//                                onAddMenuItemToCartClick(menuItem)
//                            }) {
//                            Text(
//                                text = "В корзину",
//                                fontSize = 16.sp,
//                                color = Color.Black,
//                            )
//
//                        }
//                    }
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                start = 2.dp,
//                                top = 4.dp,
//                                end = 2.dp,
//                                bottom = 4.dp
//                            ),
//                        text = "${menuItem.weight} кг",
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Left
//                    )
//                    Text(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(
//                                start = 2.dp,
//                                top = 4.dp,
//                                end = 2.dp,
//                                bottom = 4.dp
//                            ),
//                        text = menuItem.description,
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Left,
//                    )
//                }
//            }
//        }
//    }
}