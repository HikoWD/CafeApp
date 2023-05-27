package by.ivan.CafeApp.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class OrderDetailsLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val menuItemsIdsText: String
)
