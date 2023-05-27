package by.ivan.CafeApp.ui.data.local.dao

import androidx.room.*
import by.ivan.CafeApp.ui.data.local.entity.MenuItemLocalModel

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemLocalModel")
    suspend fun getAll(): List<MenuItemLocalModel>

    @Query("SELECT * FROM MenuItemLocalModel WHERE id = :id")
    suspend fun getById(id: Int): MenuItemLocalModel

    @Query("SELECT * FROM MenuItemLocalModel WHERE title like '%' || :title || '%'")
    suspend fun getByTitle(title: String): List<MenuItemLocalModel>

    @Query("SELECT * FROM MenuItemLocalModel WHERE categoryId = :categoryId")
    suspend fun getByCategory(categoryId: Int): List<MenuItemLocalModel>

    @Insert
    suspend fun saveMenuItems(menuItemLocalModel: List<MenuItemLocalModel>)

    @Update
    suspend fun edit(menuItemLocalModel: MenuItemLocalModel)

    @Delete
    suspend fun remove(menuItemLocalModel: MenuItemLocalModel)

    @Query("DELETE FROM MenuItemLocalModel")
    suspend fun removeAll()
}