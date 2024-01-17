package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import by.ivan.CafeApp.data.remote.model.OrderRemoteModelList

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
    var timestamp: String
)

fun OrderRemoteModelList.OrderRemoteModel.toLocalModel() = OrderLocalModel(
    id = id,
    orderDetails = "${orderDetails.id}-${orderDetails.menuItemsIdsText}",
    tableId = tableId,
    timestamp = timestamp.substring(0, 10) //todo
)