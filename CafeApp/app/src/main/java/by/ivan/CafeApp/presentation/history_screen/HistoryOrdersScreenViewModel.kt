package by.ivan.CafeApp.presentation.history_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.order.usecase.GetOrdersUseCase
import by.ivan.CafeApp.domain.order.usecase.SearchNewOrdersUseCase
import by.ivan.CafeApp.domain.result.CompletableResult
import by.ivan.CafeApp.domain.table.usecase.GetCurrentTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryOrdersScreenViewModel @Inject constructor(
    private val getOrdersUseCase: GetOrdersUseCase,
    private val searchNewOrdersUseCase: SearchNewOrdersUseCase,
    private val getCurrentTableUseCase: GetCurrentTableUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(HistoryOrdersScreenUiState())
    val uiState: StateFlow<HistoryOrdersScreenUiState> = _uiState

    init {
        getOrders()
    }

    fun getOrders() {
        viewModelScope.launch {
            searchNewOrder().join()
            getOrdersUseCase().collect { orders ->
                _uiState.update {
                    it.copy(orders = orders)
                }
            }
        }
    }

    fun searchNewOrder(): Job {
        return viewModelScope.launch {
            _uiState.value =
                _uiState.value.copy(historyOrdersScreenState = HistoryOrdersScreenState.Loading)
            val currentTable = getCurrentTableUseCase().firstOrNull()
            currentTable?.let {
                when (val result = searchNewOrdersUseCase(table = currentTable)) {
                    is CompletableResult.Success -> {
                        _uiState.value =
                            _uiState.value.copy(historyOrdersScreenState = HistoryOrdersScreenState.Loaded)
                    }

                    is CompletableResult.Error -> {
                        _uiState.value = _uiState.value.copy(
                            historyOrdersScreenState = HistoryOrdersScreenState.Error(errorMessage = result.errorMessage)
                        )
                    }

                    else -> {}
                }
            }
        }
    }
}