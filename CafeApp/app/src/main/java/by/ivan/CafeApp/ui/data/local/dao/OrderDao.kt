package by.ivan.CafeApp.ui.data.local.dao

import androidx.room.*
import by.ivan.CafeApp.ui.data.local.entity.MenuItemLocalModel
import by.ivan.CafeApp.ui.data.local.entity.OrderLocalModel

@Dao
interface OrderDao {
    @Query("SELECT * FROM OrderLocalModel")
    fun getAll(): List<OrderLocalModel>

    @Query("SELECT * FROM OrderLocalModel WHERE id = :id")
    fun getById(id: Int): OrderLocalModel

    @Insert
    suspend fun saveOrders(orders: List<OrderLocalModel>)

    @Insert
    fun insert(orderLocalModel: OrderLocalModel)

    @Query("DELETE FROM OrderLocalModel")
    suspend fun removeAll()
}