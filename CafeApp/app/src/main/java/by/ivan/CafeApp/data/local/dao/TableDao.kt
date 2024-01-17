package by.ivan.CafeApp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TableDao {
    @Query("SELECT * FROM TableLocalModel")
    fun observeAll(): Flow<List<TableLocalModel>>

    @Query("SELECT * FROM TableLocalModel WHERE id = :id")
    fun getById(id: Int): Flow<TableLocalModel>

    @Insert
    fun insert(tableLocalModel: TableLocalModel)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveTables(tableLocalModels: List<TableLocalModel>)

    @Update
    fun edit(tableLocalModel: TableLocalModel)

    @Delete
    fun remove(tableLocalModel: TableLocalModel)

    @Query("DELETE FROM TableLocalModel")
    suspend fun removeAll()
}