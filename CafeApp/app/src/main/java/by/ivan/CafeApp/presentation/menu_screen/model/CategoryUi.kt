package by.ivan.CafeApp.presentation.menu_screen.model

import by.ivan.CafeApp.domain.category.model.Category

data class CategoryUi(val id: Int, val title: String, val selected: Boolean)

fun Category.toUiModel() = CategoryUi(
    id = id,
    title = title,
    selected = false
)