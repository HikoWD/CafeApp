package by.ivan.CafeApp.presentation.search_screen

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
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    navigator: DestinationsNavigator,
    paddingValuesParent: PaddingValues,
    onMenuButtonClick: () -> Unit,
) {
    val state by viewModel.uiState.collectAsState()

    SearchItemsScreen(
        viewModel = viewModel,
        menuItems = state.menuItems,
        isActiveSearchBar = state.isActiveSearchBar,
        searchHistoryItems = state.searchHistoryItems,
        menuItemTitleForSearch = state.menuItemTitleForSearch,
        paddingValuesParent = paddingValuesParent,
        onClearMenuItemsClick = { viewModel.clearMenuItems() },
        onUpdateMenuItemTitleInput = { viewModel.updateMenuItemTitleForSearch(it) },
        onClearMenuItemTitleForSearchClick = { viewModel.clearMenuItemTitleForSearch() },
        onAddMenuItemToCartClick = { viewModel.addMenuItemToCart(it) },
        onAddSearchHistoryItemEffect = { viewModel.addSearchHistoryItem(it) },
        showSearchBar = { viewModel.showSearchBar(it) },
        onMenuButtonClick = onMenuButtonClick
    )
}

@Composable
private fun SearchItemsScreen(
    viewModel: SearchItemsScreenViewModel,
    menuItemTitleForSearch: String = "",
    isActiveSearchBar: Boolean = false,
    menuItems: List<MenuItem> = listOf(),
    searchHistoryItems: List<SearchHistoryItem> = listOf(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    onClearMenuItemsClick: () -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit = {},
    onClearMenuItemTitleForSearchClick: () -> Unit = {},
    onAddMenuItemToCartClick: (menuItem: MenuItem) -> Unit = {},
    onAddSearchHistoryItemEffect: (SearchHistoryItem) -> Unit = {},
    showSearchBar: (Boolean) -> Unit = {},
    onMenuButtonClick: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchTopBar(
                viewModel = viewModel,
                menuItems = menuItems,
                isActiveSearchBar = isActiveSearchBar,
                menuItemTitleForSearch = menuItemTitleForSearch,
                paddingValuesParent = paddingValuesParent,
                onClearMenuItemsClick = onClearMenuItemsClick,
                onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
                onClearMenuItemTitleForSearchClick = onClearMenuItemTitleForSearchClick,
                onAddMenuItemToCartClick = onAddMenuItemToCartClick,
                onAddSearchHistoryItemEffect = onAddSearchHistoryItemEffect,
                showSearchBar = showSearchBar,
                onMenuButtonClick = onMenuButtonClick
            )
        },
        content = { paddingValuesChild ->
            SearchItemsMain(
                viewModel = viewModel,
                searchHistoryItems = searchHistoryItems,
                paddingValuesParent = paddingValuesParent,
                paddingValuesChild = paddingValuesChild,
                onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
                showSearchBar = showSearchBar
            )
        }
    )
}