package by.ivan.CafeApp.presentation.cart_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.domain.cart.usecase.AddMenuItemToCartUseCase
import by.ivan.CafeApp.domain.cart.usecase.DecreaseCountCartItemUseCase
import by.ivan.CafeApp.domain.cart.usecase.GetCartItemsUseCase
import by.ivan.CafeApp.domain.cart.usecase.PostCartItemsUseCase
import by.ivan.CafeApp.domain.cart.usecase.RemoveCartItemsUseCase
import by.ivan.CafeApp.domain.menu.model.MenuItem
import by.ivan.CafeApp.domain.result.PublicationOrderResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartScreenViewModel @Inject constructor(
    private val getCartItemsUseCase: GetCartItemsUseCase,
    private val addMenuItemToCartUseCase: AddMenuItemToCartUseCase,
    private val decreaseCountCartItemUseCase: DecreaseCountCartItemUseCase,
    private val removeCartItemsUseCase: RemoveCartItemsUseCase,
    //private val postMenuItemsUseCase: PostMenuItemsUseCase,
    private val postCartItemsUseCase: PostCartItemsUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(CartScreenUiState())
    val uiState: StateFlow<CartScreenUiState> = _uiState

    fun getCartItems(): Job {
        return viewModelScope.launch {
            getCartItemsUseCase().collect { cartItems ->
                Log.d("3333333333", "getCartItems")
                _uiState.update {
                    it.copy(cartItems = cartItems)
                }
            }
        }
    }

    fun addCartItem(menuItem: MenuItem) {
        viewModelScope.launch {
            addMenuItemToCartUseCase(menuItem = menuItem)
        }
    }

    fun decreaseCountCartItem(menuItem: MenuItem) {
        viewModelScope.launch {
            decreaseCountCartItemUseCase(menuItem = menuItem)
        }
    }

    fun removeCartItems() {
        viewModelScope.launch {
            removeCartItemsUseCase()
        }
    }

    fun postOrder() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(orderPostState = OrderPostState.LOADING)
            delay(2000L) //todo
            when (val result = postCartItemsUseCase(cartItems = _uiState.value.cartItems)) {
                is PublicationOrderResult.Success -> {
                    if (result.order != null) {
                        _uiState.value = _uiState.value.copy(
                            orderResult = result.order,
                            orderPostState = OrderPostState.POSTED
                        )
                        removeCartItems()
                    }
                }

                is PublicationOrderResult.Error -> {
                    _uiState.value = _uiState.value.copy(
                        errorResult = result.errorMessage ?: "Something Went Wrong",
                        orderPostState = OrderPostState.ERROR
                    )
                }

                else -> {}
            }
        }
    }

    fun changeStateToIdle(){ //todo
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(orderPostState = OrderPostState.IDLE)
        }
    }
}