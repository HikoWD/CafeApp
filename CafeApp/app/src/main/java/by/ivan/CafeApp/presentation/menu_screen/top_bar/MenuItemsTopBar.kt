package by.ivan.CafeApp.presentation.menu_screen.top_bar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
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
    onDrawerOpenClick: () -> Unit
) {
    MenuItemsTopBar(
        menuItems = menuItems,
        onGetMenuItemsSortedByAlphabetClick = onGetMenuItemsSortedByAlphabetClick,
        onGetMenuItemsSortedByPriceClick = onGetMenuItemsSortedByPriceClick,
        onDrawerOpenClick = onDrawerOpenClick,
    )
}

@Composable
private fun MenuItemsTopBar(
    menuItems: List<MenuItem> = listOf(),
    onGetMenuItemsSortedByAlphabetClick: (categoryId: Int) -> Unit = {},
    onGetMenuItemsSortedByPriceClick: (categoryId: Int) -> Unit = {},
    onDrawerOpenClick: () -> Unit = {},
) {
    TopAppBar(modifier = Modifier.fillMaxWidth(),
        navigationIcon = {
            IconButton(onClick = {
                onDrawerOpenClick()
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Menu",
                    tint = Color.White
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
        title = { /*TODO*/ })
}