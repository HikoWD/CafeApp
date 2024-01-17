package by.ivan.CafeApp.data.local.dao

import androidx.room.*
import by.ivan.CafeApp.data.local.entity.OrderLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM OrderLocalModel")
    fun observeAll(): Flow<List<OrderLocalModel>>

    @Query("SELECT * FROM OrderLocalModel WHERE id = :id")
    fun getById(id: Int): OrderLocalModel

    @Insert
    suspend fun saveOrders(orders: List<OrderLocalModel>)

    @Insert
    fun insert(orderLocalModel: OrderLocalModel)

    @Query("DELETE FROM OrderLocalModel")
    suspend fun removeAll()
}