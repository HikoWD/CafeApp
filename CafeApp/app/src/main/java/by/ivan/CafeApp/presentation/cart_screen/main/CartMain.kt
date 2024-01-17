package by.ivan.CafeApp.presentation.cart_screen.main

import android.util.Log
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
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
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
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
    onNavigateOrderScreenSuccessScreen: (Order) -> Unit,
    onGetCartItemsEffect: () -> Unit,
    onAddMenuItemToCartClick: (MenuItem) -> Unit,
    onRemoveMenuItemFromCartClick: (MenuItem) -> Unit,
    onPostOrderClick: () -> Unit,
) {
    val state = viewModel.uiState.collectAsState()

    val localContext = LocalContext.current

    //todo
    DisposableEffect(Unit) {
        val job = viewModel.getCartItems()

        onDispose {
            job.cancel()
        }
    }

//    LaunchedEffect(state) {
//        onGetCartItemsEffect()
//    }


    Crossfade(
        targetState = state.value.orderPostState,
        animationSpec = tween(
            durationMillis = 800,
            easing = LinearEasing
        ),
        label = ""
    ) { orderPostState ->
        when (orderPostState) {
            OrderPostState.LOADING -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable(
                            indication = null, // disable ripple effect
                            interactionSource = remember { MutableInteractionSource() },
                            onClick = { }
                        ), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            OrderPostState.POSTED -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize(), contentAlignment = Alignment.Center
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
                    onNavigateOrderScreenSuccessScreen(state.value.orderResult)
                    viewModel.changeStateToIdle()
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
                    Toast.makeText(localContext, state.value.errorResult, Toast.LENGTH_LONG)
                        .show()
                    delay(1200L)
                    viewModel.changeStateToIdle()
                }
            }

            OrderPostState.IDLE -> {}
        }
    }

    CartMain(
        cartItems = state.value.cartItems,
        paddingValuesParent = paddingValuesParent,
        paddingValuesChild = paddingValuesChild,
        isNotEmptyUiState = state.value.cartItems.isNotEmpty(),
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
        modifier = Modifier.fillMaxSize()
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 250.dp),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.weight(0.8f)
        ) {
            itemsIndexed(items = cartItems) { index, item ->
                Column(modifier = Modifier.fillMaxSize()) {
                    Card(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 5.dp, vertical = 5.dp)
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
                                    model = "${by.ivan.CafeApp.data.Constants.URL}/${item.menuItem.image}",
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
                var enabled by remember {
                    mutableStateOf(true)
                }

                LaunchedEffect(enabled) {
                    if (enabled) return@LaunchedEffect
                    else delay(3000L)
                    enabled = true
                }

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isNotEmptyUiState && enabled,
                    onClick = {
                        onPostOrderClick()
                        enabled = false
                    }
                ) {
                    Text(
                        text = "Оформить за ",
                        color = Color.White,
                        fontSize = 24.sp
                    )
                    AnimatedContent(
                        targetState = cartItems.sumOf { it.menuItem.price * it.count }
                            .roundTo(decimals = 1),
                        label = "",
                    ) { displayNumber ->
                        Text(
                            text = "${displayNumber} ",
                            color = Color.White,
                            fontSize = 24.sp
                        )
                    }
                    Text(
                        text = "руб.",
                        color = Color.White,
                        fontSize = 24.sp
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