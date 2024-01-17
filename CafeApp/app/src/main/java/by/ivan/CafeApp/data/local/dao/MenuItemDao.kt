package by.ivan.CafeApp.data.local.dao

import androidx.room.*
import by.ivan.CafeApp.data.local.entity.MenuItemLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemLocalModel")
    fun observeAll(): Flow<List<MenuItemLocalModel>>

    @Query("SELECT * FROM MenuItemLocalModel WHERE id = :id")
    suspend fun getById(id: Int): MenuItemLocalModel

    @Query("SELECT * FROM MenuItemLocalModel WHERE title like '%' || :title || '%'")
    fun getByTitle(title: String): Flow<List<MenuItemLocalModel>>

    @Query("SELECT * FROM MenuItemLocalModel WHERE categoryId = :categoryId")
    fun getByCategoryId(categoryId: Int): Flow<List<MenuItemLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMenuItems(menuItemLocalModel: List<MenuItemLocalModel>)

    @Update
    suspend fun edit(menuItemLocalModel: MenuItemLocalModel)

    @Delete
    suspend fun remove(menuItemLocalModel: MenuItemLocalModel)

    @Query("DELETE FROM MenuItemLocalModel WHERE categoryId = :categoryId")
    suspend fun removeAllByCategoryId(categoryId: Int)

    @Query("DELETE FROM MenuItemLocalModel")
    suspend fun removeAll()
}