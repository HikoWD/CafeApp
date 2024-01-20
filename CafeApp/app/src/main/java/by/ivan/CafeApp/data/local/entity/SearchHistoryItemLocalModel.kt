package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem

@Entity
data class SearchHistoryItemLocalModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val query: String,
    val timestamp: String
)

fun SearchHistoryItem.toLocalModel() = SearchHistoryItemLocalModel(
    id = id,
    query = query,
    timestamp = timestamp
)