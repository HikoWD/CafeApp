package by.ivan.CafeApp.presentation.order_success_screen.main

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.order_success_screen.OrderSuccessScreenState
import by.ivan.CafeApp.presentation.order_success_screen.OrderSuccessScreenViewModel
import coil.compose.AsyncImage

@Composable
fun OrderSuccessScreenMain(
    viewModel: OrderSuccessScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    orderSuccessScreenState: OrderSuccessScreenState,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
    onNavigateToMenuItemsScreenClick: () -> Unit,
) {
    val localContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = orderSuccessScreenState,
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
                is OrderSuccessScreenState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                is OrderSuccessScreenState.Empty -> {
                    Toast.makeText(localContext, "Пусто", Toast.LENGTH_LONG).show()
                    OrderSuccessScreenMain(
                        menuItems = menuItems,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild,
                        onNavigateToMenuItemsScreenClick = onNavigateToMenuItemsScreenClick,
                    )
                }

                is OrderSuccessScreenState.Loaded -> {
                    OrderSuccessScreenMain(
                        menuItems = menuItems,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild,
                        onNavigateToMenuItemsScreenClick = onNavigateToMenuItemsScreenClick,
                    )
                }

                is OrderSuccessScreenState.Idle -> {}

                else -> {}
            }
        }
    }
}

@Composable
private fun OrderSuccessScreenMain(
    menuItems: List<MenuItem> = listOf(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    paddingValuesChild: PaddingValues = PaddingValues(2.dp),
    onNavigateToMenuItemsScreenClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValuesChild.calculateTopPadding(),
                bottom = paddingValuesParent.calculateBottomPadding()
            )
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 300.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(0.9f)
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
                .weight(0.1f)
                .shadow(
                    elevation = 14.dp,
                    clip = true,
                    shape = RectangleShape
                ),
            shape = RoundedCornerShape(0.dp),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 0.dp
            ),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.background,
            ),
            border = BorderStroke(0.dp, Color.LightGray)
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
                TextButton(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                    ),
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { onNavigateToMenuItemsScreenClick() }
                ) {
                    Text(
                        text = "OK",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}
