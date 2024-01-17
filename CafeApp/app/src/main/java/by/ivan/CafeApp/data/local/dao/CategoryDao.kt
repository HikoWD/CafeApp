package by.ivan.CafeApp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.ivan.CafeApp.data.local.entity.CategoryLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryLocalModel")
    fun observeAll(): Flow<List<CategoryLocalModel>>

    @Query("SELECT * FROM CategoryLocalModel WHERE id = :id")
    fun getById(id: Int): Flow<CategoryLocalModel>

    @Insert
    suspend fun insert(categoryLocalModel: CategoryLocalModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCategories(categoryLocalModelList: List<CategoryLocalModel>)

    @Update
    suspend fun edit(categoryLocalModel: CategoryLocalModel)

    @Delete
    suspend fun remove(categoryLocalModel: CategoryLocalModel)

    @Query("DELETE FROM CategoryLocalModel")
    suspend fun removeAll()
}