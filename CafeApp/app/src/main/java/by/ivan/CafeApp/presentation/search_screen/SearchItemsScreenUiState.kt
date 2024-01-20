package by.ivan.CafeApp.presentation.search_screen

import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem

data class SearchItemsScreenUiState(
    val menuItems: List<MenuItem> = listOf(),
    val searchHistory: List<SearchHistoryItem> = listOf(),
    val menuItemTitleForSearch: String = "",
)
