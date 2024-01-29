package by.ivan.CafeApp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import by.ivan.CafeApp.data.local.entity.SearchHistoryItemLocalModel
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchHistoryDao {
    //todo
    @Query("SELECT * FROM SearchHistoryItemLocalModel WHERE `query` IN " +
            "(SELECT `query` FROM SearchHistoryItemLocalModel " +
            "GROUP BY `query` HAVING MAX(timestamp)) ORDER BY timestamp DESC")
    fun observeAll(): Flow<List<SearchHistoryItemLocalModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: SearchHistoryItemLocalModel)
}