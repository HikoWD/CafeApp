package by.ivan.CafeApp.data.repo

import by.ivan.CafeApp.data.datasource.CartItemLocalDatasource
import by.ivan.CafeApp.data.datasource.CartItemRemoteDatasource
import by.ivan.CafeApp.data.local.datastore.DataStoreTable
import by.ivan.CafeApp.data.local.entity.CartItemLocalModel
import by.ivan.CafeApp.data.remote.model.ResponseErrorMessage
import by.ivan.CafeApp.domain.cart.model.CartItem
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.model.OrderDetails
import by.ivan.CafeApp.domain.order.model.OrderParams
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CartItemRepository @Inject constructor(
    private val cartItemLocalDatasource: CartItemLocalDatasource,
    private val cartItemRemoteDatasource: CartItemRemoteDatasource,
    private val dataStoreTable: DataStoreTable
) {
    suspend fun getAll(): Flow<List<CartItemLocalModel>> {
        return cartItemLocalDatasource.getAll()
    }

    suspend fun getById(id: Int): Flow<CartItemLocalModel> {
        return cartItemLocalDatasource.getById(id = id)
    }

    suspend fun createItemOrIncreaseCount(cartItemLocalModel: CartItemLocalModel) {
        val cartItemFromDb = getById(id = cartItemLocalModel.menuItemId).firstOrNull()
        if (cartItemFromDb != null) {
            increaseCount(cartItemLocalModel = cartItemFromDb)
        } else {
            createItem(cartItemLocalModel = cartItemLocalModel)
        }
    }

    private suspend fun createItem(cartItemLocalModel: CartItemLocalModel) {
        cartItemLocalDatasource.add(
            cartItemLocalModel = cartItemLocalModel
        )
    }

    private suspend fun increaseCount(cartItemLocalModel: CartItemLocalModel) {
        cartItemLocalDatasource.edit(
            cartItemLocalModel = cartItemLocalModel.copy(count = cartItemLocalModel.count + 1)
        )
    }

    suspend fun removeItemOrDecreaseCount(cartItemLocalModel: CartItemLocalModel) {
        with(cartItemLocalModel) {
            if (cartItemLocalDatasource.count(id = this.menuItemId) != 1) {
                cartItemLocalDatasource.decreaseCount(id = this.menuItemId)
            } else {
                cartItemLocalDatasource.remove(cartItemLocalModel = cartItemLocalModel)
            }
        }
    }

    suspend fun postCartItems(cartItems: List<CartItem>): NetworkResponse<Order, ResponseErrorMessage> {
        val currentTable = dataStoreTable.getCurrentTable.firstOrNull()
        val orderDetails = OrderDetails()

        currentTable?.let {
            orderDetails.menuItemsIdsText += formationOrderDetailsMenuItemsIds(cartItems = cartItems)

            return cartItemRemoteDatasource.postCartItems(
                orderParams = OrderParams(
                    orderDetails = orderDetails,
                    table = currentTable
                )
            )

        } ?: run { //todo needed refactoring
            return NetworkResponse.UnknownError(
                error = throw Error("table is null"),
                response = null
            )
        }
    }

    //todo rename
    private fun formationOrderDetailsMenuItemsIds(cartItems: List<CartItem>): String {
        var ids = ""
        cartItems.forEach { cartItem ->
            for (i in 1..cartItem.count) {
                ids += "${cartItem.menuItem.id};"
            }
        }
        return ids.removeSuffix(";")
    }

    suspend fun removeAll() {
        cartItemLocalDatasource.removeAll()
    }
}