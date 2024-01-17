package by.ivan.CafeApp.presentation.order_details_screen

import by.ivan.CafeApp.domain.menu.model.MenuItem

data class OrderDetailsScreenUiState(
    val menuItems: List<MenuItem> = listOf()
)
