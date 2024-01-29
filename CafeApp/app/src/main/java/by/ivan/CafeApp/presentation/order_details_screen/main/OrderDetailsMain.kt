package by.ivan.CafeApp.presentation.order_details_screen.main

import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.order_details_screen.OrderDetailsScreenState
import by.ivan.CafeApp.presentation.order_details_screen.OrderDetailsScreenViewModel
import coil.compose.AsyncImage

@Composable
fun OrderDetailsMain(
    viewModel: OrderDetailsScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues
) {
    val localContext = LocalContext.current

    val state by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Crossfade(
            targetState = state.orderDetailsScreenState,
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
                is OrderDetailsScreenState.Loading -> {
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

                is OrderDetailsScreenState.Loaded -> {
                    OrderDetailsMain(
                        menuItems = menuItems,
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild,
                    )
                }

                is OrderDetailsScreenState.Empty -> {
                    Toast.makeText(localContext, "Пусто", Toast.LENGTH_LONG).show()
                    OrderDetailsMain(
                        paddingValuesParent = paddingValuesParent,
                        paddingValuesChild = paddingValuesChild,
                        menuItems = menuItems
                    )
                }

                is OrderDetailsScreenState.Idle -> {}

                else -> {}
            }
        }
    }
}

@Composable
private fun OrderDetailsMain(
    menuItems: List<MenuItem> = listOf(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    paddingValuesChild: PaddingValues = PaddingValues(2.dp),
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValuesChild.calculateTopPadding(),
                bottom = paddingValuesParent.calculateBottomPadding()
            )
    ) {
        LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 250.dp)) {
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
}