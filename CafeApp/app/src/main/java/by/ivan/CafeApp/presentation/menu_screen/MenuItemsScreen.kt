package by.ivan.CafeApp.presentation.menu_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.MenuNavGraph
import by.ivan.CafeApp.presentation.menu_screen.main.MenuItemsMain
import by.ivan.CafeApp.presentation.menu_screen.model.CategoryUi
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
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    MenuItemsScreen(
        viewModel = viewModel,
        categories = state.categories,
        menuItems = state.menuItems,
        categoriesScreenState = state.categoriesScreenState,
        menuItemsScreenState = state.menuItemsScreenState,
        paddingValuesParent = paddingValuesParent,
        onGetMenuItemsByCategoryIdClick = { viewModel.selectCategory(selectedCategory = it) },
        onGetMenuItemsSortedByAlphabetClick = { viewModel.getMenuItemsSortedByAlphabet() },
        onGetMenuItemsSortedByPriceClick = { viewModel.getMenuItemsSortedByPrice() },
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(menuItem = it) },
        onMenuButtonClick = onMenuButtonClick,
    )
}

@Composable
private fun MenuItemsScreen(
    viewModel: MenuItemsScreenViewModel,
    categories: List<CategoryUi> = listOf(),
    menuItems: List<MenuItem> = listOf(),
    categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    menuItemsScreenState: MenuItemsScreenState = MenuItemsScreenState.Idle,
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onGetMenuItemsByCategoryIdClick: (CategoryUi) -> Unit = {},
    onGetMenuItemsSortedByAlphabetClick: () -> Unit = {},
    onGetMenuItemsSortedByPriceClick: () -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onMenuButtonClick: () -> Unit = {},
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MenuItemsTopBar(
                viewModel = viewModel,
                categories = categories,
                categoriesScreenState = categoriesScreenState,
                onSelectCategoryClick = onGetMenuItemsByCategoryIdClick,
                onGetMenuItemsSortedByAlphabetClick = onGetMenuItemsSortedByAlphabetClick,
                onGetMenuItemsSortedByPriceClick = onGetMenuItemsSortedByPriceClick,
                onMenuButtonClick = onMenuButtonClick
            )
        },
        content = { paddingValuesChild ->
            MenuItemsMain(
                viewModel = viewModel,
                menuItems = menuItems,
                menuItemsScreenState = menuItemsScreenState,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild
            )
        }
    )
}