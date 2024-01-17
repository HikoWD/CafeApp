package by.ivan.CafeApp.domain.cart.model

import by.ivan.CafeApp.data.local.entity.CartItemLocalModel
import by.ivan.CafeApp.data.local.entity.MenuItemLocalModel
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.menu.model.toDomain

data class CartItem(
    val menuItem: MenuItem,
    val count: Int
)

fun CartItemLocalModel.toDomain(menuItem: MenuItem) = CartItem(
    menuItem = menuItem,
    count = count
)

fun MenuItemLocalModel.toDomain(value: Int = 0) = CartItem(
    menuItem = this.toDomain(),
    count = value
)