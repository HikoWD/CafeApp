package by.ivan.CafeApp.presentation

import com.ramcosta.composedestinations.annotation.NavGraph
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@NavGraph
annotation class MenuNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = false)
@NavGraph
annotation class SearchNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = false)
@NavGraph
annotation class CartNavGraph(
    val start: Boolean = false
)

@RootNavGraph(start = false)
@NavGraph
annotation class HistoryNavGraph(
    val start: Boolean = false
)