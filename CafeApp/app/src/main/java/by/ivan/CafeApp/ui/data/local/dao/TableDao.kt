package by.ivan.CafeApp.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel

@Dao
interface TableDao {
    @Query("SELECT * FROM TableLocalModel")
    fun getAll(): List<TableLocalModel>

    @Query("SELECT * FROM TableLocalModel WHERE id = :id")
    fun getById(id: Int): TableLocalModel

    @Insert
    fun insert(tableLocalModel: TableLocalModel)

    @Insert
    fun saveTables(tableLocalModels: List<TableLocalModel>)

    @Update
    fun edit(tableLocalModel: TableLocalModel)

    @Delete
    fun remove(tableLocalModel: TableLocalModel)

    @Query("DELETE FROM TableLocalModel")
    suspend fun removeAll()
}