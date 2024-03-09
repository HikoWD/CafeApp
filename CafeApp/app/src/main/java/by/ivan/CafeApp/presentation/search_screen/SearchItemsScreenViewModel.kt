package by.ivan.CafeApp.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.cart.usecase.AddMenuItemToCartUseCase
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByTitleUseCase
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import by.ivan.CafeApp.domain.search_history.usecase.AddSearchHistoryItemUseCase
import by.ivan.CafeApp.domain.search_history.usecase.GetAllSearchHistoryItemsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchItemsScreenViewModel @Inject constructor(
    private val getMenuItemsByTitleUseCase: GetMenuItemsByTitleUseCase,
    private val addMenuItemToCartUseCase: AddMenuItemToCartUseCase,
    private val getAllSearchHistoryItemsUseCase: GetAllSearchHistoryItemsUseCase,
    private val addSearchHistoryItemUseCase: AddSearchHistoryItemUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchItemsScreenUiState())
    val uiState: StateFlow<SearchItemsScreenUiState> = _uiState

    init {
        viewModelScope.launch {
            _uiState
                .filter { it.menuItemTitleForSearch.isNotBlank() }
                .collect { getMenuItemsByTitle(title = it.menuItemTitleForSearch) }
        }
        getAllSearchHistoryItems()
    }

    private fun getMenuItemsByTitle(title: String) {
        viewModelScope.launch {
            val menuItems = getMenuItemsByTitleUseCase(title = title)
            _uiState.update {
                it.copy(menuItems = menuItems)
            }
        }
    }

    private fun getAllSearchHistoryItems() {
        viewModelScope.launch {
            getAllSearchHistoryItemsUseCase().collect { historyItems ->
                _uiState.update {
                    it.copy(searchHistoryItems = historyItems)
                }
            }
        }
    }

    fun addSearchHistoryItem(item: SearchHistoryItem) {
        viewModelScope.launch {
            addSearchHistoryItemUseCase(searchHistoryItem = item)
        }
    }

    fun addMenuItemToCart(menuItem: MenuItem) {
        viewModelScope.launch {
            addMenuItemToCartUseCase(menuItem = menuItem)
        }
    }

    fun updateMenuItemTitleForSearch(title: String) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(menuItemTitleForSearch = title)
            }
        }
    }

    fun clearMenuItemTitleForSearch() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(menuItemTitleForSearch = "")
            }
        }
    }

    fun clearMenuItems() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(menuItems = listOf())
            }
        }
    }

    fun showSearchBar(show: Boolean) {
        _uiState.update {
            it.copy(isActiveSearchBar = show)
        }
    }
}
