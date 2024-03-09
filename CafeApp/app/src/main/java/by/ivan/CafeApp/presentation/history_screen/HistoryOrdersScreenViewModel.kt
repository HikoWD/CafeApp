package by.ivan.CafeApp.presentation.history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.order.usecase.GetAllOrdersUseCase
import by.ivan.CafeApp.domain.order.usecase.LoadOrdersByTableUseCase
import by.ivan.CafeApp.domain.result.CompletableResult
import by.ivan.CafeApp.domain.table.usecase.GetCurrentTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryOrdersScreenViewModel @Inject constructor(
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val loadOrdersByTableUseCase: LoadOrdersByTableUseCase,
    private val getCurrentTableUseCase: GetCurrentTableUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryOrdersScreenUiState())
    val uiState: StateFlow<HistoryOrdersScreenUiState> = _uiState

    init {
        loadOrdersByTable()
        getOrders()
    }

    fun getOrders() {
        viewModelScope.launch {
            getAllOrdersUseCase().collect { orders ->
                _uiState.update {
                    it.copy(orders = orders)
                }
            }
        }
    }

    fun loadOrdersByTable() {
        viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(historyOrdersScreenState = HistoryOrdersScreenState.Loading)
            getCurrentTableUseCase().collect { currentTable ->
                currentTable?.let {
                    when (val result = loadOrdersByTableUseCase(table = currentTable)) {
                        is CompletableResult.Success -> {
                            _uiState.value =
                                _uiState.value.copy(historyOrdersScreenState = HistoryOrdersScreenState.Loaded)
                        }

                        is CompletableResult.Error -> {
                            _uiState.value = _uiState.value.copy(
                                historyOrdersScreenState = HistoryOrdersScreenState.Error(
                                    errorMessage = result.errorMessage
                                )
                            )
                        }

                        else -> {}
                    }
                }
            }
        }
    }
}