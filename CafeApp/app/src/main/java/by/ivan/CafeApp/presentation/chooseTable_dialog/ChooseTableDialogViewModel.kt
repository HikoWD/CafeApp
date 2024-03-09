package by.ivan.CafeApp.presentation.chooseTable_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.order.usecase.LoadOrdersByTableUseCase
import by.ivan.CafeApp.domain.result.CompletableResult
import by.ivan.CafeApp.domain.table.model.Table
import by.ivan.CafeApp.domain.table.usecase.GetCurrentTableUseCase
import by.ivan.CafeApp.domain.table.usecase.GetTablesUseCase
import by.ivan.CafeApp.domain.table.usecase.LoadTablesUseCase
import by.ivan.CafeApp.domain.table.usecase.SaveTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseTableDialogViewModel @Inject constructor(
    private val getTablesUseCase: GetTablesUseCase,
    private val loadTablesUseCase: LoadTablesUseCase,
    private val getCurrentTableUseCase: GetCurrentTableUseCase,
    private val saveTableUseCase: SaveTableUseCase,
    private val loadOrdersByTableUseCase: LoadOrdersByTableUseCase
    //todo если вызвать ChooseTableDialog в истории - удаляются заказы
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChooseTableDialogUiState())
    val uiState: StateFlow<ChooseTableDialogUiState> = _uiState

    init {
        loadTables()
    }

    fun loadTables() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(tableDialogState = TableDialogState.Loading)
            when (val result = loadTablesUseCase()) {
                is CompletableResult.Success -> {
                    _uiState.value = _uiState.value.copy(tableDialogState = TableDialogState.Loaded)
                }

                is CompletableResult.Error -> {
                    _uiState.value =
                        _uiState.value.copy(tableDialogState = TableDialogState.Error(errorMessage = result.errorMessage))
                }

                else -> {}
            }
        }
    }

    fun getTables() {
        viewModelScope.launch {
            getTablesUseCase().collect { tables ->
                _uiState.update {
                    it.copy(tables = tables)
                }
            }
        }
    }

    fun getCurrentTable() {
        viewModelScope.launch {
            getCurrentTableUseCase().collect { table ->
                if (table != null) {
                    _uiState.update {
                        it.copy(currentTable = table)
                    }
                        //.also { searchNewOrdersUseCase(table) } //todo ошибка выше
                }
            }
        }
    }

    fun saveTable(table: Table) {
        viewModelScope.launch {
            saveTableUseCase(table = table).also {
                loadOrdersByTableUseCase(table = table)
            }
        }
    }
}