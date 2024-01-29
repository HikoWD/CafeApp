package by.ivan.CafeApp.presentation.menu_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel

@Composable
fun MenuItemsTopBar(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    menuItems: List<MenuItem>,
    onGetMenuItemsSortedByAlphabetClick: (categoryId: Int) -> Unit,
    onGetMenuItemsSortedByPriceClick: (categoryId: Int) -> Unit,
    onMenuButtonClick: () -> Unit
) {
    MenuItemsTopBar(
        menuItems = menuItems,
        onGetMenuItemsSortedByAlphabetClick = onGetMenuItemsSortedByAlphabetClick,
        onGetMenuItemsSortedByPriceClick = onGetMenuItemsSortedByPriceClick,
        onMenuButtonClick = onMenuButtonClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuItemsTopBar(
    menuItems: List<MenuItem> = listOf(),
    onGetMenuItemsSortedByAlphabetClick: (categoryId: Int) -> Unit = {},
    onGetMenuItemsSortedByPriceClick: (categoryId: Int) -> Unit = {},
    onMenuButtonClick: () -> Unit = {},
) {
    TopAppBar(
        modifier = Modifier.fillMaxWidth(),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
        ),
        navigationIcon = {
            IconButton(onClick = {
                onMenuButtonClick()
            }) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = stringResource(id = R.string.drawer_menu_description)
                )
            }
        },
        actions = {
            IconButton(onClick = {
                onGetMenuItemsSortedByAlphabetClick(menuItems[0].categoryId)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_sort_by_alpha_24),
                    contentDescription = "Sort by alphabet",
                    tint = Color.White
                )
            }
            IconButton(onClick = {
                onGetMenuItemsSortedByPriceClick(menuItems[0].categoryId)
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_attach_money_24),
                    contentDescription = "Sort by price",
                    tint = Color.White
                )
            }
        },
        title = {}
    )
}