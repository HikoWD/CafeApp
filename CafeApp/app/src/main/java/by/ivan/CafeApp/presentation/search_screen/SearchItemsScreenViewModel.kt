package by.ivan.CafeApp.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.cart.usecase.AddMenuItemToCartUseCase
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByTitleUseCase
import by.ivan.CafeApp.domain.search_history.AddSearchHistoryItemUseCase
import by.ivan.CafeApp.domain.search_history.GetAllSearchHistoryItemsUseCase
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    fun getMenuItemsByTitle() {
        viewModelScope.launch {
            getMenuItemsByTitleUseCase(title = _uiState.value.menuItemTitleForSearch).collect { menuItems ->
                _uiState.update {
                    it.copy(menuItems = menuItems)
                }
            }
        }
    }

    fun getAllSearchHistoryItems(): Job{
        return viewModelScope.launch {
           getAllSearchHistoryItemsUseCase().collect{ items ->
               _uiState.update {
                   it.copy(searchHistory = items)
               }
           }
        }
    }

    fun addSearchHistoryItem(item: SearchHistoryItem){
        viewModelScope.launch{
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

    fun clearMenuItems(){
        viewModelScope.launch {
            _uiState.update {
                it.copy(menuItems = listOf())
            }
        }
    }
}
