package by.ivan.CafeApp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.ivan.CafeApp.data.local.entity.CartItemLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM CartItemLocalModel")
    fun observeAll(): Flow<List<CartItemLocalModel>>

    @Query("SELECT count FROM CartItemLocalModel WHERE menuItemId = :id")
    suspend fun count(id: Int): Int?

    @Query("SELECT * FROM CartItemLocalModel WHERE menuItemId = :id")
    fun getById(id: Int): Flow<CartItemLocalModel>

    @Insert
    suspend fun add(cartItemLocalModel: CartItemLocalModel)

    @Update
    suspend fun edit(cartItemLocalModel: CartItemLocalModel)

    @Delete
    suspend fun remove(cartItemLocalModel: CartItemLocalModel)

    @Query("UPDATE CartItemLocalModel SET count = count - 1 WHERE menuItemId = :id")
    suspend fun decreaseCount(id: Int)

    @Query("DELETE FROM CartItemLocalModel")
    suspend fun removeAll()
}