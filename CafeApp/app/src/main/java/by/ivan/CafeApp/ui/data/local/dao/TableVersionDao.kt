package by.ivan.CafeApp.ui.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import by.ivan.CafeApp.ui.data.local.entity.CategoryLocalModel
import by.ivan.CafeApp.ui.data.local.entity.MenuItemLocalModel
import by.ivan.CafeApp.ui.data.local.entity.TableLocalModel
import by.ivan.CafeApp.ui.data.local.entity.TableVersionLocalModel

@Dao
interface TableVersionDao {
    @Query("SELECT * FROM TableVersionLocalModel")
    suspend fun getAll(): List<TableVersionLocalModel>

    @Query("SELECT * FROM TableVersionLocalModel WHERE tableName = :tableName")
    suspend fun getByName(tableName: String): TableVersionLocalModel

    @Insert
    suspend fun insert(tableVersionLocalModel: TableVersionLocalModel)

    @Update
    suspend fun edit(tableVersionLocalModel: TableVersionLocalModel)

    @Insert
    suspend fun saveTableVersions(tableVersionLocalModel: List<TableVersionLocalModel>)

    @Query("DELETE FROM TableVersionLocalModel")
    suspend fun removeAll()
}