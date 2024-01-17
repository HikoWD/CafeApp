package by.ivan.CafeApp.presentation.search_screen

import by.ivan.CafeApp.domain.menu.model.MenuItem

data class SearchItemsScreenUiState(
    val menuItems: List<MenuItem> = listOf(),
    val menuItemTitleForSearch: String = ""
)
