package by.ivan.CafeApp.domain.category.model

import by.ivan.CafeApp.data.local.entity.CategoryLocalModel

data class Category(val id: Int = -1, val title: String = "")

fun CategoryLocalModel.toDomain() = Category(
    id = id,
    title = title
)
