package by.ivan.CafeApp.ui.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity
data class TableVersionLocalModel (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val tableName: String,
    var version: Int
)