package by.ivan.CafeApp.presentation.cart_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CartTopBar(
    onRemoveAllMenuItemsFromCartClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "Корзина",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        },
        actions = {
            IconButton(
                onClick = { onRemoveAllMenuItemsFromCartClick() }
            ) {
                Icon(
                    modifier = Modifier.size(30.dp),
                    imageVector = Icons.Default.Delete,
                    tint = Color.White,
                    contentDescription = "Remove all items"
                )
            }

        }
    )
}