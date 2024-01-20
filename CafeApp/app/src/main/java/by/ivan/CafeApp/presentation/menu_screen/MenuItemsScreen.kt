package by.ivan.CafeApp.presentation.menu_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.category.model.Category
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.MenuNavGraph
import by.ivan.CafeApp.presentation.chooseTable_dialog.ChooseTableDialogViewModel
import by.ivan.CafeApp.presentation.menu_screen.drawer.MenuItemsScreenDrawer
import by.ivan.CafeApp.presentation.menu_screen.main.MenuItemsMain
import by.ivan.CafeApp.presentation.menu_screen.top_bar.MenuItemsTopBar
import com.ramcosta.composedestinations.annotation.Destination
import kotlinx.coroutines.launch

@MenuNavGraph(start = true)
@Destination
@Composable
fun MenuItemsScreen(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    chooseTableDialogViewModel: ChooseTableDialogViewModel = hiltViewModel(),
    paddingValuesParent: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()

    MenuItemsScreen(
        viewModel = viewModel,
        chooseTableDialogViewModel = chooseTableDialogViewModel,
        categories = state.categories,
        menuItems = state.menuItems,
        categoriesScreenState = state.categoriesScreenState,
        menuItemsScreenState = state.menuItemsScreenState,
        showLoginDialogState = state.showLoginDialog,
        showChooseTableDialogState = state.showChooseTableDialog,
        onGetMenuItemsByCategoryIdClick = { viewModel.getMenuItemsByCategoryId(categoryId = it) },
        onGetMenuItemsSortedByAlphabetClick = { viewModel.getMenuItemsSortedByAlphabet(categoryId = it) },
        onGetMenuItemsSortedByPriceClick = {
            viewModel.getMenuItemsSortedByPrice(categoryId = it)
        },
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(menuItem = it) },
        onShowLoginDialogClick = { viewModel.showLoginDialog(show = true) },
        onShowChooseTableDialogClick = { viewModel.showChooseTableDialog(show = true) },
        onChooseTableDialogDismissRequest = { viewModel.showChooseTableDialog(show = false) },
        onLoginDialogDismissRequest = { viewModel.showLoginDialog(show = false) },
        paddingValuesParent = paddingValuesParent
    )
}

@Composable
private fun MenuItemsScreen(
    viewModel: MenuItemsScreenViewModel,
    chooseTableDialogViewModel: ChooseTableDialogViewModel,
    categories: List<Category> = listOf(),
    menuItems: List<MenuItem> = listOf(),
    categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    menuItemsScreenState: MenuItemsScreenState = MenuItemsScreenState.Idle,
    showLoginDialogState: Boolean = false,
    showChooseTableDialogState: Boolean = false,
    onGetMenuItemsByCategoryIdClick: (categoryId: Int) -> Unit = {},
    onGetMenuItemsSortedByAlphabetClick: (categoryId: Int) -> Unit = {},
    onGetMenuItemsSortedByPriceClick: (categoryId: Int) -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onShowLoginDialogClick: () -> Unit = {},
    onShowChooseTableDialogClick: () -> Unit = {},
    onChooseTableDialogDismissRequest: () -> Unit = {},
    onLoginDialogDismissRequest: () -> Unit = {},
    paddingValuesParent: PaddingValues = PaddingValues(2.dp)
) {
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        scaffoldState = scaffoldState,
        drawerContent = {
            MenuItemsScreenDrawer(
                viewModel = viewModel,
                chooseTableDialogViewModel = chooseTableDialogViewModel,
                showLoginDialogState = showLoginDialogState,
                showChooseTableDialogState = showChooseTableDialogState,
                onShowLoginDialogClick = onShowLoginDialogClick,
                onLoginDialogDismissRequest = onLoginDialogDismissRequest,
                onShowChooseTableDialogClick = onShowChooseTableDialogClick,
                onChooseTableDialogDismissRequest = onChooseTableDialogDismissRequest,
                onDrawerCloseEffect = { coroutineScope.launch { scaffoldState.drawerState.close() } },
            )
        },
        topBar = {
            MenuItemsTopBar(
                viewModel = viewModel,
                menuItems = menuItems,
                onGetMenuItemsSortedByAlphabetClick = onGetMenuItemsSortedByAlphabetClick,
                onGetMenuItemsSortedByPriceClick = onGetMenuItemsSortedByPriceClick,
                onDrawerOpenClick = { coroutineScope.launch { scaffoldState.drawerState.open() } })
        },
        content = { padding ->
            MenuItemsMain(
                viewModel = viewModel,
                categories = categories,
                menuItems = menuItems,
                categoriesScreenState = categoriesScreenState,
                menuItemsScreenState = menuItemsScreenState,
                onGetMenuItemsByCategoryIdClick = onGetMenuItemsByCategoryIdClick,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = padding
            )
        }
    )
}