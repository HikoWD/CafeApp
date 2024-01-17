package by.ivan.CafeApp.presentation.order_success_screen

import by.ivan.CafeApp.domain.menu.model.MenuItem

data class OrderSuccessScreenUiState(
    val menuItems: List<MenuItem> = listOf()
)