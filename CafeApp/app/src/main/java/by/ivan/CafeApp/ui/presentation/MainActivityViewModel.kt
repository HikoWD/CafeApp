package by.ivan.CafeApp.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.ivan.CafeApp.ui.data.local.DataStore.DataStoreTable
import by.ivan.CafeApp.ui.data.models.Category
import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.models.OrderDetails
import by.ivan.CafeApp.ui.data.models.OrderParams
import by.ivan.CafeApp.ui.domain.usecase.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getMenuItemUseCase: GetMenuItemUseCase,
    private val getTablesUseCase: GetTablesUseCase,
    private val getMenuItemsByCategoryUseCase: GetMenuItemsByCategoryUseCase,
//    private val getBasketUseCase: GetBasketUseCase,
//    private val addMenuItemToBasketUseCase: AddMenuItemToBasketUseCase,
//    private val removeMenuItemFromBasketUseCase: RemoveMenuItemFromBasketUseCase,
    private val getMenuItemsByTitleUseCase: GetMenuItemsByTitleUseCase,
    private val postMenuItemsUseCase: PostMenuItemsUseCase,
    private val getMenuItemsSortedByAlphabetUseCase: GetMenuItemsSortedByAlphabetUseCase,
    private val getMenuItemsSortedByPriceUseCase: GetMenuItemsSortedByPriceUseCase,
    private val startDatabaseWorkerUseCase: StartDatabaseWorkerUseCase,
    private val getOrdersByTableUseCase: GetOrdersByTableUseCase,
    private val getOrderDetailsByIdUseCase: GetOrderDetailsByIdUseCase,
    private val getCategoryByIdUseCase: GetCategoryByIdUseCase,
    private val getMenuItemByIdUseCase: GetMenuItemByIdUseCase,
    private val dataStoreTable: DataStoreTable,
    private val checkInternetConnectionUseCase: CheckInternetConnectionUseCase,
) : ViewModel() {
    //хранит 1 значение и доставляет его всем своим подписчикам
    private val _uiState = MutableStateFlow(MainActivityUiState())
    val uiState: StateFlow<MainActivityUiState> = _uiState

    fun getDataStoreTable(): DataStoreTable {
        return dataStoreTable
    }

    fun getCategories(): Job {
        return viewModelScope.launch {
            val categories = getCategoriesUseCase()
            _uiState.update {
                it.copy(categories = categories)
            }
        }
    }

    fun getMenuItems(): Job {
        return viewModelScope.launch {
            getMenuItemUseCase()
        }
    }

    fun getTables(): Job {
        return viewModelScope.launch {
            val tables = getTablesUseCase()
            _uiState.update {
                it.copy(tables = tables)
            }
        }
    }

    fun getMenuItemsByCategory(categoryId: Int) {
        viewModelScope.launch {
            val menuItems = getMenuItemUseCase(categoryId = categoryId)
            _uiState.update {
                it.copy(menuItems = menuItems)
            }
        }
    }

    fun getMenuItemsByTitle(title: String) {
        viewModelScope.launch {
            val menuItems = getMenuItemsByTitleUseCase(title = title)
            _uiState.update {
                it.copy(menuItems = menuItems)
            }
        }
    }

    fun getCategoryById(id: Int, categories: MutableList<Category>) {
        viewModelScope.launch {
            val category = getCategoryByIdUseCase(id = id)
            categories.add(category)
            _uiState.update {
                it.copy(basketCategories = categories)
            }
        }
    }

    fun getMenuItemsSortedByAlphabet(categoryId: Int) {
        viewModelScope.launch {
            val menuItems = getMenuItemsSortedByAlphabetUseCase(categoryId)
            _uiState.update {
                it.copy(menuItems = menuItems)
            }
        }
    }

    fun getMenuItemsSortedByPrice(categoryId: Int) {
        viewModelScope.launch {
            val menuItems = getMenuItemsSortedByPriceUseCase(categoryId)
            _uiState.update {
                it.copy(menuItems = menuItems)
            }
        }
    }

    fun getOrders() {

    }

    fun getMenuItemById(id: Int, items: MutableList<MenuItem>) {
        viewModelScope.launch {
            val item = getMenuItemByIdUseCase(id = id)
            items.add(item)
            _uiState.update {
                it.copy(menuItemsInOrder = items)
            }
        }
    }

    fun getOrdersByTable(tableId: Int) {
        viewModelScope.launch {
            val orders = getOrdersByTableUseCase(tableId = tableId)
            _uiState.update {
                it.copy(orders = orders)
            }
        }
    }

    fun postMenuItems(orderParams: OrderParams): by.ivan.CafeApp.ui.data.models.Order {
        var res: by.ivan.CafeApp.ui.data.models.Order = by.ivan.CafeApp.ui.data.models.Order(
            -1,
            OrderDetails(), -1, ""
            //  Date()
        )
        viewModelScope.launch {
            res = postMenuItemsUseCase(orderParams)
        }
        return res
    }

    fun isConnect(): Boolean {
        return checkInternetConnectionUseCase.isConnect()
    }

    init {
        viewModelScope.launch {
            getCategories().join()
            getMenuItems().join()
            getTables().join()
            getMenuItemsByCategory(_uiState.value.categories[0].id)
        }

        GlobalScope.launch {
            startDatabaseWorkerUseCase()
        }
    }
}