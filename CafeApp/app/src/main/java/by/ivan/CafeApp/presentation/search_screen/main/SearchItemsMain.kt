package by.ivan.CafeApp.presentation.search_screen.main

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.data.Constants
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.search_screen.SearchItemsScreenViewModel
import coil.compose.AsyncImage

@Composable
fun SearchItemsMain(
    viewModel: SearchItemsScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit,
    paddingValuesChild: PaddingValues
) {
    SearchItemsMain(
        menuItems = menuItems,
        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
        paddingValuesChild = paddingValuesChild
    )
}

@Composable
private fun SearchItemsMain(
    menuItems: List<MenuItem> = listOf(),
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    paddingValuesChild: PaddingValues = PaddingValues(2.dp)
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 420.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(paddingValuesChild)
    ) {
        itemsIndexed(items = menuItems) { index, menuItem ->
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
                        text = menuItem.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    AsyncImage(
                        model = "${Constants.URL}/${menuItem.image}",
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
                            text = "${menuItem.price} р",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Black
                        )
                        Button(colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                            onClick = {
                                onAddMenuItemToCartClick(menuItem)
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
                        text = "${menuItem.weight} кг",
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
                        text = menuItem.description,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Left,
                    )
                }
            }
        }
    }
}