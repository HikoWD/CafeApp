package by.ivan.CafeApp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import by.ivan.CafeApp.ui.data.remote.model.TableRemoteModelList
import by.ivan.CafeApp.ui.domain.table.model.State
import by.ivan.CafeApp.ui.domain.table.model.toState

@Entity
data class TableLocalModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val title: String,
    val state: State
)

fun TableRemoteModelList.TableRemoteModel.toLocalModel() = TableLocalModel(
    id = id,
    title = title,
    state = state.toState()
)

