package by.ivan.CafeApp.ui.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = CategoryLocalModel::class,
        parentColumns = ["id"],
        childColumns = ["categoryId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class MenuItemLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val categoryId: Int,
    val image: String,
    val title: String,
    val description: String,
    val price: Double,
    val weight: Double
)
