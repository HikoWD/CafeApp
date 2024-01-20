package by.ivan.CafeApp.presentation.chooseTable_dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.result.CompletableResult
import by.ivan.CafeApp.domain.table.model.Table
import by.ivan.CafeApp.domain.table.usecase.GetCurrentTableUseCase
import by.ivan.CafeApp.domain.table.usecase.GetTablesUseCase
import by.ivan.CafeApp.domain.table.usecase.SaveTableUseCase
import by.ivan.CafeApp.domain.table.usecase.SearchNewTablesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChooseTableDialogViewModel @Inject constructor(
    private val getTablesUseCase: GetTablesUseCase,
    private val searchNewTablesUseCase: SearchNewTablesUseCase,
    private val getCurrentTableUseCase: GetCurrentTableUseCase,
    private val saveTableUseCase: SaveTableUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(ChooseTableDialogUiState())
    val uiState: StateFlow<ChooseTableDialogUiState> = _uiState

    fun searchNewTable(): Job {
        return viewModelScope.launch {
            _uiState.value = _uiState.value.copy(tableDialogState = TableDialogState.Loading)
            when (val result = searchNewTablesUseCase()) {
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

    fun getTables(): Job {
        return viewModelScope.launch {
            searchNewTable().join()
            getTablesUseCase().collect { tables ->
                _uiState.update {
                    it.copy(tables = tables)
                }
            }
        }
    }

    fun getCurrentTable(): Job {
        return viewModelScope.launch {
            getCurrentTableUseCase().collect{ table ->
                if (table != null) {
                    _uiState.update {
                        it.copy(currentTable = table)
                    }
                }
            }
        }
    }

    fun saveTable(table: Table) {
        viewModelScope.launch {
            saveTableUseCase(table = table)
        }
    }
}