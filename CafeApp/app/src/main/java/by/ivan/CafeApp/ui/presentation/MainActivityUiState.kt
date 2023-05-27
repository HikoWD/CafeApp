package by.ivan.CafeApp.ui.presentation

import by.ivan.CafeApp.ui.data.models.Category
import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.models.Table

data class MainActivityUiState(
    val menuItems: List<MenuItem> = listOf(),
    val categories: List<Category> = listOf(),
    val tables: List<Table> = listOf(),
    val basketCategories: List<Category> = listOf(),
    val orders: List<by.ivan.CafeApp.ui.data.models.Order> = listOf(),
    val menuItemsInOrder: List<MenuItem> = listOf()
)