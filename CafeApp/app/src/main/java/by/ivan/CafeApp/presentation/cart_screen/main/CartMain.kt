package by.ivan.CafeApp.presentation.cart_screen.main

import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ContentTransform
import androidx.compose.animation.Crossfade
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.data.Constants
import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.presentation.cart_screen.CartScreenViewModel
import by.ivan.CafeApp.presentation.cart_screen.OrderPostState
import coil.compose.AsyncImage
import kotlinx.coroutines.delay
import kotlin.math.pow
import kotlin.math.round

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun CartMain(
    viewModel: CartScreenViewModel = hiltViewModel(),
    cartItems: List<CartItem>,
    isNotEmptyUiState: Boolean,
    orderResult: Order,
    errorResult: String,
    orderPostState: OrderPostState,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
    onNavigateOrderScreenSuccessScreen: (Order) -> Unit,
    onAddMenuItemToCartClick: (MenuItem) -> Unit,
    onRemoveMenuItemFromCartClick: (MenuItem) -> Unit,
    onPostOrderClick: () -> Unit,
    onChangeStateToIdleEffect: () -> Unit,
) {
    val localContext = LocalContext.current

    Crossfade(
        targetState = orderPostState,
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearEasing
        ),
        label = ""
    ) { state ->
        when (state) {
            OrderPostState.LOADING -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            indication = null, // disable ripple effect
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { }
                        ),
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            OrderPostState.POSTED -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .zIndex(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(96.dp),
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = "successfully posted",
                        tint = Color.Green
                    )
                }
                LaunchedEffect(Unit) {
                    delay(800L)
                    onNavigateOrderScreenSuccessScreen(orderResult)
                    onChangeStateToIdleEffect()
                }
            }

            OrderPostState.ERROR -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(), contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(96.dp),
                        painter = painterResource(R.drawable.baseline_error_24),
                        contentDescription = "error",
                        tint = Color.Red
                    )
                }
                LaunchedEffect(Unit) {
                    Toast.makeText(localContext, errorResult, Toast.LENGTH_LONG)
                        .show()
                    delay(1200L)
                    onChangeStateToIdleEffect()
                }
            }

            OrderPostState.IDLE -> {}
        }
    }

    CartMain(
        cartItems = cartItems,
        paddingValuesParent = paddingValuesParent,
        paddingValuesChild = paddingValuesChild,
        isNotEmptyUiState = isNotEmptyUiState,
        onAddMenuItemToCartClick = onAddMenuItemToCartClick,
        onRemoveMenuItemFromCartClick = onRemoveMenuItemFromCartClick,
        onPostOrderClick = onPostOrderClick
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun CartMain(
    cartItems: List<CartItem>,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
    isNotEmptyUiState: Boolean = false,
    onAddMenuItemToCartClick: (MenuItem) -> Unit = {},
    onRemoveMenuItemFromCartClick: (MenuItem) -> Unit = {},
    onPostOrderClick: () -> Unit = {},
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
            columns = GridCells.Adaptive(minSize = 250.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(0.9f)
        ) {
            itemsIndexed(items = cartItems) { index, item ->
                Column(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp, vertical = 5.dp),
                        shape = RoundedCornerShape(20.dp),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.background,
                        ),
                        border = BorderStroke(0.5.dp, Color.Black),
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AsyncImage(
                                    model = "${Constants.URL}/${item.menuItem.image}",
                                    contentDescription = "product picture",
                                    modifier = Modifier
                                        .height(70.dp)
                                        .width(70.dp)
                                )
                                Text(text = item.menuItem.title, fontSize = 20.sp)
                            }
                            Divider(
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(2.dp)
                                    .width(1.dp)
                            )
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(5.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                AnimatedContent(
                                    targetState = (item.menuItem.price * item.count).roundTo(1),
                                    label = "",
                                ) { displayNumber ->
                                    Text(
                                        text = "${displayNumber}",
                                        fontSize = 26.sp
                                    )
                                }
                                Row(
                                    horizontalArrangement = Arrangement.Center,
                                ) {
                                    Box(contentAlignment = Alignment.CenterStart) {
                                        IconButton(onClick = {
                                            onRemoveMenuItemFromCartClick(item.menuItem)
                                        }) {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                painter = painterResource(id = R.drawable.baseline_remove_24),
                                                contentDescription = "Minus item",
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                    AnimatedContent(
                                        targetState = item.count,
                                        transitionSpec = {
                                            if (targetState > initialState) {
                                                ContentTransform(
                                                    targetContentEnter = slideInVertically { height -> height } + fadeIn(),
                                                    initialContentExit = slideOutVertically { height -> -height } + fadeOut()
                                                )
                                            } else {
                                                ContentTransform(
                                                    targetContentEnter = slideInVertically { height -> -height } + fadeIn(),
                                                    initialContentExit = slideOutVertically { height -> height } + fadeOut()
                                                )
                                            }
                                        }, label = ""
                                    ) { displayNumber ->
                                        Text(
                                            modifier = Modifier.padding(8.dp),
                                            text = "${displayNumber}",
                                            fontSize = 22.sp,
                                            textAlign = TextAlign.Center
                                        )
                                    }

                                    Box(contentAlignment = Alignment.CenterEnd) {
                                        IconButton(onClick = {
                                            onAddMenuItemToCartClick(item.menuItem)
                                        }) {
                                            Icon(
                                                modifier = Modifier.size(24.dp),
                                                painter = painterResource(id = R.drawable.baseline_add_24),
                                                contentDescription = "Plus item",
                                                tint = Color.Black
                                            )
                                        }
                                    }
                                }
                            }
                        }
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
                var enabled by remember {
                    mutableStateOf(true)
                }

                LaunchedEffect(enabled) {
                    if (enabled) return@LaunchedEffect
                    else delay(3000L)
                    enabled = true
                }

                TextButton(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isNotEmptyUiState && enabled,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    onClick = {
                        onPostOrderClick()
                        enabled = false
                    }
                ) {
                    Text(
                        text = "Оформить за ",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleLarge
                    )

                    //on price change
                    AnimatedContent(
                        targetState = cartItems.sumOf { it.menuItem.price * it.count }
                            .roundTo(decimals = 1),
                        label = "",
                    ) { displayNumber ->
                        Text(
                            text = "${displayNumber} ",
                            color = MaterialTheme.colorScheme.background,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }

                    Text(
                        text = "руб.",
                        color = MaterialTheme.colorScheme.background,
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }
        }
    }
}

private fun Double.roundTo(decimals: Int): Double {
    val factor = 10.0.pow(decimals)
    return round(this * factor) / factor
}