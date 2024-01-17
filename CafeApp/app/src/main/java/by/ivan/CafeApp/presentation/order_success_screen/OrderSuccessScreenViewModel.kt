package by.ivan.CafeApp.presentation.order_success_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByOrderItemsIdsUseCase
import by.ivan.CafeApp.domain.order.model.Order
import by.ivan.CafeApp.domain.order.usecase.SearchNewOrdersUseCase
import by.ivan.CafeApp.domain.table.usecase.GetCurrentTableUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderSuccessScreenViewModel @Inject constructor(
    private val getMenuItemsByOrderItemsIdsUseCase: GetMenuItemsByOrderItemsIdsUseCase,
    private val currentTableUseCase: GetCurrentTableUseCase,
    private val searchNewOrdersUseCase: SearchNewOrdersUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderSuccessScreenUiState())
    val uiState: StateFlow<OrderSuccessScreenUiState> = _uiState

    fun getMenuItemsByOrderItemsIds(order: Order) {
        viewModelScope.launch {
            val menuItems = getMenuItemsByOrderItemsIdsUseCase(order = order)
            _uiState.update {
                it.copy(menuItems = menuItems)
            }.also {
                currentTableUseCase().firstOrNull()?.let{
                    searchNewOrdersUseCase(tableId = it.id)
                }
            }
        }
    }
}