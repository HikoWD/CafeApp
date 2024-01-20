package by.ivan.CafeApp.domain.search_history.model

import by.ivan.CafeApp.data.local.entity.SearchHistoryItemLocalModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class SearchHistoryItem(
    val id: Int,
    val query: String,
    val timestamp: String
)

fun SearchHistoryItemLocalModel.toDomain() = SearchHistoryItem(
    id = id,
    query = query,
    timestamp = extractTimeFromDateTime(dateTimeString = timestamp)
)

fun extractTimeFromDateTime(dateTimeString: String): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS")
    val localDateTime = LocalDateTime.parse(dateTimeString, formatter)
    val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    return localDateTime.format(timeFormatter)
}
