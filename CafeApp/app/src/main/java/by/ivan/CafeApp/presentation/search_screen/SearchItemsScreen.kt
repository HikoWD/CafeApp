package by.ivan.CafeApp.presentation.search_screen

import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
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

    DisposableEffect(Unit) {
        val job = viewModel.getAllSearchHistoryItems()

        Log.d("3333333333", "${state.searchHistory}")
        onDispose {
            job.cancel()
        }
    }

    SearchItemsScreen(
        viewModel = viewModel,
        menuItems = state.menuItems,
        searchHistory = state.searchHistory,
        paddingValuesParent = paddingValuesParent,
        menuItemTitleForSearch = state.menuItemTitleForSearch,
        onClearMenuItemsClick = { viewModel.clearMenuItems() },
        onGetMenuItemsByTitleUpdate = { viewModel.getMenuItemsByTitle() },
        onUpdateMenuItemTitleInput = { viewModel.updateMenuItemTitleForSearch(it) },
        onClearMenuItemTitleForSearchClick = { viewModel.clearMenuItemTitleForSearch() },
        onPopBackStackClick = { navigator.popBackStack() },
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(menuItem = it) },
        onAddSearchHistoryItemEffect = { viewModel.addSearchHistoryItem(it) }
    )
}

@Composable
private fun SearchItemsScreen(
    viewModel: SearchItemsScreenViewModel,
    menuItems: List<MenuItem> = listOf(),
    searchHistory: List<SearchHistoryItem> = listOf(),
    menuItemTitleForSearch: String = "",
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onClearMenuItemsClick: () -> Unit = {},
    onGetMenuItemsByTitleUpdate: () -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit = {},
    onClearMenuItemTitleForSearchClick: () -> Unit = {},
    onPopBackStackClick: () -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onAddSearchHistoryItemEffect: (SearchHistoryItem) -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                viewModel = viewModel,
                menuItems = menuItems,
                menuItemTitleForSearch = menuItemTitleForSearch,
                paddingValuesParent = paddingValuesParent,
                onClearMenuItemsClick = onClearMenuItemsClick,
                onGetMenuItemsByTitleUpdate = onGetMenuItemsByTitleUpdate,
                onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
                onClearMenuItemTitleForSearchClick = onClearMenuItemTitleForSearchClick,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                onAddSearchHistoryItemEffect = onAddSearchHistoryItemEffect,
                onPopBackStackClick = onPopBackStackClick
            )
        },
        content = {
            println(it)
            SearchItemsMain(
                viewModel = viewModel,
                menuItems = menuItems,
                searchHistory = searchHistory,
                onGetMenuItemsByTitleUpdate = onGetMenuItemsByTitleUpdate,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = it
            )
        }
    )
}