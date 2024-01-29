package by.ivan.CafeApp.presentation.order_details_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.menu.usecase.GetMenuItemsByOrderItemsIdsUseCase
import by.ivan.CafeApp.domain.order.model.Order
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsScreenViewModel @Inject constructor(
    private val getMenuItemsByOrderItemsIdsUseCase: GetMenuItemsByOrderItemsIdsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(OrderDetailsScreenUiState())
    val uiState: StateFlow<OrderDetailsScreenUiState> = _uiState

    fun getMenuItemsByOrderItemsId(order: Order) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                orderDetailsScreenState = OrderDetailsScreenState.Loading
            )

            getMenuItemsByOrderItemsIdsUseCase(order = order).collect { menuItems ->
                when {
                    menuItems.isEmpty() -> {
                        _uiState.update {
                            it.copy(orderDetailsScreenState = OrderDetailsScreenState.Empty)
                        }
                    }

                    else -> {
                        _uiState.update {
                            it.copy(
                                menuItems = menuItems,
                                orderDetailsScreenState = OrderDetailsScreenState.Loaded
                            )
                        }
                    }
                }
            }
        }
    }
}