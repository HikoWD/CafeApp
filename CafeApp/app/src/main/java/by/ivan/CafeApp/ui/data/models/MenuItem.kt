package by.ivan.CafeApp.ui.data.models

import by.ivan.CafeApp.ui.data.remote.model.MenuItems

data class MenuItem(
    val categoryId: Int = -1,
    val image: String = "",
    val description: String = "",
    val id: Int = -1,
    val price: Double = 0.0,
    val title: String = "",
    val weight: Double = 0.0
)