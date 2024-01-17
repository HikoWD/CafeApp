package by.ivan.CafeApp.presentation.order_success_screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.order_success_screen.OrderSuccessScreenViewModel
import coil.compose.AsyncImage

@Composable
fun OrderSuccessScreenMain(
    viewModel: OrderSuccessScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    onNavigateToMenuClick: () -> Unit,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
) {
    OrderSuccessScreenMain(
        menuItems = menuItems,
        paddingValuesParent = paddingValuesParent,
        onNavigateToMenuClick = onNavigateToMenuClick,
    )
}

@Composable
private fun OrderSuccessScreenMain(
    menuItems: List<MenuItem> = listOf(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onNavigateToMenuClick: () -> Unit,
) {
    Column(modifier = Modifier.fillMaxSize()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .weight(0.8f)
        ) {
            itemsIndexed(items = menuItems) { index, item ->
                Card(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 15.dp, vertical = 5.dp)
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2f)
                .padding(bottom = paddingValuesParent.calculateBottomPadding())
                .shadow(
                    elevation = 14.dp,
                    clip = true,
                    shape = RectangleShape
                ),
            border = BorderStroke(0.dp, Color.LightGray),
            backgroundColor = Color.White
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        start = 7.dp,
                        top = 2.dp,
                        end = 7.dp,
                    ),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = {
                        onNavigateToMenuClick()
                    }
                ) {
                    Text(
                        text = "OK",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}
