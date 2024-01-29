package by.ivan.CafeApp.presentation.menu_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import by.ivan.CafeApp.domain.category.model.Category
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.MenuNavGraph
import by.ivan.CafeApp.presentation.menu_screen.main.MenuItemsMain
import by.ivan.CafeApp.presentation.menu_screen.top_bar.MenuItemsTopBar
import com.ramcosta.composedestinations.annotation.Destination

@MenuNavGraph(start = true)
@Destination
@Composable
fun MenuItemsScreen(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    paddingValuesParent: PaddingValues,
    onMenuButtonClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    MenuItemsScreen(
        viewModel = viewModel,
        categories = state.categories,
        menuItems = state.menuItems,
        categoriesScreenState = state.categoriesScreenState,
        menuItemsScreenState = state.menuItemsScreenState,
        paddingValuesParent = paddingValuesParent,
        onGetMenuItemsByCategoryIdClick = { viewModel.getMenuItemsByCategoryId(categoryId = it) },
        onGetMenuItemsSortedByAlphabetClick = { viewModel.getMenuItemsSortedByAlphabet(categoryId = it) },
        onGetMenuItemsSortedByPriceClick = {
            viewModel.getMenuItemsSortedByPrice(categoryId = it)
        },
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(menuItem = it) },
        onMenuButtonClick = onMenuButtonClick,
    )
}

@Composable
private fun MenuItemsScreen(
    viewModel: MenuItemsScreenViewModel,
    categories: List<Category> = listOf(),
    menuItems: List<MenuItem> = listOf(),
    categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    menuItemsScreenState: MenuItemsScreenState = MenuItemsScreenState.Idle,
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit = {},
    onGetMenuItemsSortedByAlphabetClick: (categoryId: Int) -> Unit = {},
    onGetMenuItemsSortedByPriceClick: (categoryId: Int) -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onMenuButtonClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MenuItemsTopBar(
                viewModel = viewModel,
                menuItems = menuItems,
                onGetMenuItemsSortedByAlphabetClick = onGetMenuItemsSortedByAlphabetClick,
                onGetMenuItemsSortedByPriceClick = onGetMenuItemsSortedByPriceClick,
                onMenuButtonClick = onMenuButtonClick
            )
        },
        content = { paddingValuesChild ->
            MenuItemsMain(
                viewModel = viewModel,
                categories = categories,
                menuItems = menuItems,
                categoriesScreenState = categoriesScreenState,
                menuItemsScreenState = menuItemsScreenState,
                onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild
            )
        }
    )
}