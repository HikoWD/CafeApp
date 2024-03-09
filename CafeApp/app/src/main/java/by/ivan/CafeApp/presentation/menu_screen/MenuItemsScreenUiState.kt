package by.ivan.CafeApp.presentation.menu_screen

import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.presentation.menu_screen.model.CategoryUi

data class MenuItemsScreenUiState(
    val categories: List<CategoryUi> = listOf(),
    val menuItems: List<MenuItem> = listOf(),
    val errorCategories: String = "",
    val errorMenuItems: String = "",
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