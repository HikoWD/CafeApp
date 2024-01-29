package by.ivan.CafeApp.presentation.menu_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.cart.usecase.AddMenuItemToCartUseCase
import by.ivan.CafeApp.domain.category.usecase.GetCategoriesUseCase
import by.ivan.CafeApp.domain.category.usecase.SearchNewCategoryUseCase
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByCategoryIdUseCase
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsSortedByAlphabetUseCase
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsSortedByPriceUseCase
import by.ivan.CafeApp.domain.menu.usecase.SearchNewMenuItemUseCase
import by.ivan.CafeApp.domain.result.CompletableResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuItemsScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val searchNewCategoryUseCase: SearchNewCategoryUseCase,
    private val searchNewMenuItemUseCase: SearchNewMenuItemUseCase,
    private val getMenuItemsByCategoryIdUseCase: GetMenuItemsByCategoryIdUseCase,
    private val getMenuItemsSortedByAlphabetUseCase: GetMenuItemsSortedByAlphabetUseCase,
    private val getMenuItemsSortedByPriceUseCase: GetMenuItemsSortedByPriceUseCase,
    private val addMenuItemToCartUseCase: AddMenuItemToCartUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuItemsScreenUiState())
    val uiState: StateFlow<MenuItemsScreenUiState> = _uiState

    init{
        getCategories()
    }

    fun searchNewCategory(): Job {
        return viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                categoriesScreenState = CategoriesScreenState.Loading
            )
            when (val result = searchNewCategoryUseCase()) {
                is CompletableResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        categoriesScreenState = CategoriesScreenState.Loaded,
                    )
                }

                is CompletableResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        categoriesScreenState = CategoriesScreenState.Error(errorMessage = result.errorMessage)
                    )
                }

                else -> {}
            }
        }
    }

    fun searchNewMenuItem(): Job {
        return viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                menuItemsScreenState = MenuItemsScreenState.Loading
            )
            when (val result = searchNewMenuItemUseCase()) {
                is CompletableResult.Success -> {
                    _uiState.value = _uiState.value.copy(
                        menuItemsScreenState = MenuItemsScreenState.Loaded,
                    )
                }

                is CompletableResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        menuItemsScreenState = MenuItemsScreenState.Error(errorMessage = result.errorMessage),
                    )
                }

                else -> {}
            }
        }
    }

    fun getCategories() {
         viewModelScope.launch {
            searchNewCategory().join()

            var isMenuItemsFetched = false
            getCategoriesUseCase().collect { categories ->
                _uiState.update {
                    it.copy(
                        categories = categories,
                    )
                }
                if (!isMenuItemsFetched && categories.isNotEmpty()) {
                    getMenuItemsByCategoryId(categoryId = categories[0].id)
                    isMenuItemsFetched = true
                }
            }
        }
    }

    fun getMenuItemsByCategoryId(categoryId: Int): Job {
        return viewModelScope.launch {
            searchNewMenuItem().join()
            getMenuItemsByCategoryIdUseCase(categoryId = categoryId).collect { menuItems ->
                _uiState.value = _uiState.value.copy(
                    menuItems = menuItems
                )
            }
        }
    }

    fun getMenuItemsSortedByAlphabet(categoryId: Int) {
        viewModelScope.launch {
            getMenuItemsSortedByAlphabetUseCase(categoryId = categoryId).collect { menuItems ->
                _uiState.update {
                    it.copy(menuItems = menuItems)
                }
            }
        }
    }

    fun getMenuItemsSortedByPrice(categoryId: Int) {
        viewModelScope.launch {
            getMenuItemsSortedByPriceUseCase(categoryId = categoryId).collect { menuItems ->
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
}