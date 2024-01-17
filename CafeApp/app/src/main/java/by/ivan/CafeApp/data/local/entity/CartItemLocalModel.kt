package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.menu.model.MenuItem

@Entity
data class CartItemLocalModel(
    @PrimaryKey(autoGenerate = false)
    val menuItemId: Int,
    val count: Int
)

fun CartItem.toLocalModel(value: Int = 0) = CartItemLocalModel(
    menuItemId = menuItem.id,
    count = count + value
)

fun MenuItem.toCartItemLocalModel(count: Int = 0) = CartItemLocalModel(
    menuItemId = id,
    count = count
)