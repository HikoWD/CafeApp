package by.ivan.CafeApp.ui.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [
    ForeignKey(
        entity = TableLocalModel::class,
        parentColumns = ["id"],
        childColumns = ["tableId"],
        onDelete = ForeignKey.CASCADE
    )
])
data class OrderLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val orderDetails: String,
    val tableId: Int,
    val timestamp: String
)
