package by.ivan.CafeApp.domain.table.model

import by.ivan.CafeApp.data.local.entity.TableLocalModel

data class Table(
    val id: Int = 1,
    val title: String = "table",
    val state: Int = 0
)

fun TableLocalModel.toDomain() = Table(
    id = id,
    title = title,
    state = state.i
)