package by.ivan.CafeApp.presentation.search_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.cart.usecase.AddMenuItemToCartUseCase
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByTitleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchItemsScreenViewModel @Inject constructor(
    private val getMenuItemsByTitleUseCase: GetMenuItemsByTitleUseCase,
    private val addMenuItemToCartUseCase: AddMenuItemToCartUseCase
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

    fun addMenuItemToCart(menuItem: MenuItem) {
        viewModelScope.launch {
            addMenuItemToCartUseCase(menuItem = menuItem)
        }
    }

    fun updateMenuItemTitleForSearch(title: String){
        viewModelScope.launch {
            _uiState.update {
                it.copy(menuItemTitleForSearch = title)
            }
        }
    }
}
