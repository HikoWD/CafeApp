package by.ivan.CafeApp.presentation

import android.support.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import by.ivan.CafeApp.R
import com.ramcosta.composedestinations.navigation.navigate

sealed class BottomNavItem(
    val graph: NavGraph,
    val icon: @Composable () -> Unit,
    @StringRes val label: Int
) {
    object MenuItems : BottomNavItem(
        graph = NavGraphs.menu,
        icon = {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home icon"
            )
        },
        label = R.string.menuItems_screen
    )

    object SearchItems : BottomNavItem(
        graph = NavGraphs.search,
        icon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search icon"
            )
        },
        label = R.string.searchItems_screen
    )

    object Cart : BottomNavItem(
        graph = NavGraphs.cart,
        icon = {
            Icon(
                imageVector = Icons.Default.ShoppingCart,
                contentDescription = "ShoppingCart icon"
            )
        },
        label = R.string.cart_screen
    )

    object OrderDetails : BottomNavItem(
        graph = NavGraphs.history,
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.baseline_history_24),
                contentDescription = "History icon"
            )
        },
        label = R.string.historyOrders_screen
    )
}

@Composable
fun MainActivityBottomBar(
    navController: NavHostController
) {
    val items = listOf(
        BottomNavItem.MenuItems,
        BottomNavItem.SearchItems,
        BottomNavItem.Cart,
        BottomNavItem.OrderDetails
    )

    MainActivityBottomBar(
        items = items,
        navController = navController,
    )
}

@Composable
private fun MainActivityBottomBar(
    items: List<BottomNavItem> = listOf(),
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(modifier = Modifier.fillMaxWidth()) {
        items.forEach { destination ->

            val isCurrentDestination =
                currentDestination?.hierarchy?.any { it.route == destination.graph.route } == true

            NavigationBarItem(
                selected = isCurrentDestination,
                onClick = {
                    navController.navigate(destination.graph) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                        // Pop up to the root of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                    }
                },
                icon = destination.icon,
                label = { Text(stringResource(destination.label)) },
            )
        }
    }
}