package by.ivan.CafeApp.domain.result

import by.ivan.CafeApp.domain.order.model.Order

sealed class PublicationOrderResult {
    data class Success(val order: Order?): PublicationOrderResult()
    data class Error(val errorMessage: String?) : PublicationOrderResult()
}