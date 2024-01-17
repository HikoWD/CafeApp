package by.ivan.CafeApp.presentation.chooseTable_dialog

import by.ivan.CafeApp.domain.table.model.Table

data class ChooseTableDialogUiState(
    val tables: List<Table> = listOf(),
    val currentTable: Table = Table(),
    val tableDialogState: TableDialogState = TableDialogState.Idle
)

sealed class TableDialogState {
    object Loading : TableDialogState()
    object Loaded : TableDialogState()
    data class Error(val errorMessage: String?) : TableDialogState()
    object Idle : TableDialogState()
}