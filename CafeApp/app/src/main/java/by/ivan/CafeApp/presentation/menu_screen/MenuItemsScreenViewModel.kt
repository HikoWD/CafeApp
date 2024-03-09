package by.ivan.CafeApp.presentation.menu_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.cart.usecase.AddMenuItemToCartUseCase
import by.ivan.CafeApp.domain.category.usecase.GetCategoriesUseCase
import by.ivan.CafeApp.domain.category.usecase.LoadCategoriesUseCase
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByCategoryIdUseCase
import by.ivan.CafeApp.domain.menu.usecase.LoadMenuItemsUseCase
import by.ivan.CafeApp.domain.result.CompletableResult
import by.ivan.CafeApp.presentation.menu_screen.model.CategoryUi
import by.ivan.CafeApp.presentation.menu_screen.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MenuItemsScreenViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val loadCategoriesUseCase: LoadCategoriesUseCase,
    private val loadMenuItemsUseCase: LoadMenuItemsUseCase,
    private val getMenuItemsByCategoryIdUseCase: GetMenuItemsByCategoryIdUseCase,
    private val addMenuItemToCartUseCase: AddMenuItemToCartUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MenuItemsScreenUiState())
    val uiState: StateFlow<MenuItemsScreenUiState> = _uiState

    init {
        loadCategories()
        loadMenuItems()
        getCategories()
    }

    fun loadCategories() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                categoriesScreenState = CategoriesScreenState.Loading
            )
            when (val result = loadCategoriesUseCase()) {
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

    fun loadMenuItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                menuItemsScreenState = MenuItemsScreenState.Loading
            )
            when (val result = loadMenuItemsUseCase()) {
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
            var isMenuItemsFetched = false
            getCategoriesUseCase().collect { categories ->
                val categoriesUiModel = categories.map {
                    it.toUiModel()
                }
                _uiState.update {
                    it.copy(
                        categories = categoriesUiModel,
                    )
                }

//                _uiState.value.categories.firstOrNull()?.let {
//                    selectCategory(selectedCategory = it)
//                }
//                if (!isMenuItemsFetched && categories.isNotEmpty()) {
//                    getMenuItemsByCategoryId(categoryId = categories[0].id)
//                    isMenuItemsFetched = true
//                }
                if (!isMenuItemsFetched && categories.isNotEmpty()) {
                    _uiState.value.categories.firstOrNull()?.let {
                        selectCategory(selectedCategory = it)
                        isMenuItemsFetched = true
                    }
                }
            }
        }
    }

    fun selectCategory(selectedCategory: CategoryUi) {
        viewModelScope.launch {
            val categories = _uiState.value.categories.map {
                it.copy(selected = it.id == selectedCategory.id)
            }

            _uiState.update {
                it.copy(categories = categories)
            }

            getMenuItemsByCategoryId(categoryId = selectedCategory.id)
        }
    }

    private fun getMenuItemsByCategoryId(categoryId: Int) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy()//todo
            getMenuItemsByCategoryIdUseCase(categoryId = categoryId).collect { menuItems ->
                _uiState.value = _uiState.value.copy(
                    menuItems = menuItems
                )
            }
        }
    }

    fun getMenuItemsSortedByAlphabet() {
        viewModelScope.launch {
            val sortedMenuItems = _uiState.value.menuItems.sortedBy { it.title }

            _uiState.update {
                it.copy(menuItems = sortedMenuItems)
            }
        }
    }

    fun getMenuItemsSortedByPrice() {
        viewModelScope.launch {
            val sortedMenuItems = _uiState.value.menuItems.sortedBy { it.price }.reversed()

            _uiState.update {
                it.copy(menuItems = sortedMenuItems)
            }
        }
    }

    fun addMenuItemToCart(menuItem: MenuItem) {
        viewModelScope.launch {
            addMenuItemToCartUseCase(menuItem = menuItem)
        }
    }
}