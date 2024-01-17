package by.ivan.CafeApp.presentation.menu_screen

import by.ivan.CafeApp.domain.category.model.Category
import by.ivan.CafeApp.domain.menu.model.MenuItem

data class MenuItemsScreenUiState(
    val categories: List<Category> = listOf(),
    val menuItems: List<MenuItem> = listOf(),
    val errorCategories: String = "",
    val errorMenuItems: String = "",
    val categoriesIsLoading: Boolean = false,
    val menuItemsIsLoading: Boolean = false,
    val showLoginDialog: Boolean = false,
    val showChooseTableDialog: Boolean = false,
    val categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    val menuItemsScreenState: MenuItemsScreenState = MenuItemsScreenState.Idle,
)

sealed class CategoriesScreenState {
    object Loading : CategoriesScreenState()
    object Loaded : CategoriesScreenState()
    data class Error(val errorMessage: String?) : CategoriesScreenState()
    object Idle : CategoriesScreenState()
}


sealed class MenuItemsScreenState {
    object Loading : MenuItemsScreenState()
    object Loaded : MenuItemsScreenState()
    data class Error(val errorMessage: String?) : MenuItemsScreenState()
    object Idle : MenuItemsScreenState()
}