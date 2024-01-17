package by.ivan.CafeApp.presentation.cart_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.presentation.cart_screen.CartScreenViewModel

@Composable
fun CartTopBar(
    viewModel: CartScreenViewModel = hiltViewModel(),
    onRemoveAllMenuItemsFromCartClick: () -> Unit
) {
    CartTopBar(
        onRemoveAllMenuItemsFromCartClick = onRemoveAllMenuItemsFromCartClick,
    )
}

@Composable
private fun CartTopBar(
    onRemoveAllMenuItemsFromCartClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        title = { Text(modifier = Modifier.fillMaxWidth(), text = "Корзина") },
        actions = {
            IconButton(
                onClick = { onRemoveAllMenuItemsFromCartClick() }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    painter = painterResource(id = R.drawable.baseline_delete_24),
                    tint = Color.White,
                    contentDescription = "Remove all items"
                )
            }

        }
    )
}