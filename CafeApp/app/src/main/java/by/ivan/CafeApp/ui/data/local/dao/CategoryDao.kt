package by.ivan.CafeApp.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.ivan.CafeApp.ui.data.local.entity.CategoryLocalModel

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryLocalModel")
    suspend fun getAll(): List<CategoryLocalModel>

    @Query("SELECT * FROM CategoryLocalModel WHERE id = :id")
    suspend fun getById(id: Int): CategoryLocalModel

    @Insert
    suspend fun insert(categoryLocalModel: CategoryLocalModel)

    @Insert
    suspend fun saveCategories(categoryLocalModelList: List<CategoryLocalModel>)

    @Update
    suspend fun edit(categoryLocalModel: CategoryLocalModel)

    @Delete
    suspend fun remove(categoryLocalModel: CategoryLocalModel)

    @Query("DELETE FROM CategoryLocalModel")
    suspend fun removeAll()
}