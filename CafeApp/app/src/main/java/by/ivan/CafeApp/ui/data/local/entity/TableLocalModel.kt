package by.ivan.CafeApp.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.ivan.CafeApp.ui.data.models.State

@Entity
data class TableLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val state: State
)
