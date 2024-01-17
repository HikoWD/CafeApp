package by.ivan.CafeApp.presentation.search_screen

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.SearchNavGraph
import by.ivan.CafeApp.presentation.search_screen.main.SearchItemsMain
import by.ivan.CafeApp.presentation.search_screen.top_bar.SearchTopBar
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@SearchNavGraph(start = true)
@Destination
@Composable
fun SearchItemsScreen(
    viewModel: SearchItemsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
    paddingValuesParent: PaddingValues,
) {
    val state by viewModel.uiState.collectAsState()

    SearchItemsScreen(
        viewModel = viewModel,
        menuItems = state.menuItems,
        menuItemTitleForSearch = state.menuItemTitleForSearch,
        onGetMenuItemsByTitleUpdate = { viewModel.getMenuItemsByTitle() },
        onUpdateMenuItemTitleInput = { viewModel.updateMenuItemTitleForSearch(it) },
        onPopBackStackClick = { navigator.popBackStack() },
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(menuItem = it) }
    )
}

@Composable
private fun SearchItemsScreen(
    viewModel: SearchItemsScreenViewModel,
    menuItems: List<MenuItem> = listOf(),
    menuItemTitleForSearch: String = "",
    onGetMenuItemsByTitleUpdate: () -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit,
    onPopBackStackClick: () -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                viewModel = viewModel,
                menuItemTitleForSearch = menuItemTitleForSearch,
                onGetMenuItemsByTitleUpdate = onGetMenuItemsByTitleUpdate,
                onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
                onPopBackStackClick = onPopBackStackClick
            )
        },
        content = {
            SearchItemsMain(
                viewModel = viewModel,
                menuItems = menuItems,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                paddingValuesChild = it
            )
        }
    )
}