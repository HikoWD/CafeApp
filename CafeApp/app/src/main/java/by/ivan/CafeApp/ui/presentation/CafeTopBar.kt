package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.ShoppingCart
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.ui.data.models.*
import kotlinx.coroutines.launch

@Composable
fun CafeTopBar(
    viewModel: MainActivityViewModel = hiltViewModel(),
    padding: PaddingValues,
    onGetMenuItemsByCategoryClick: (Int) -> Unit,
    topBarState: MutableState<Boolean>,
    onNavigateToSearchTopBarClick: () -> Unit,
    postMenuItems: (OrderParams) -> by.ivan.CafeApp.ui.data.models.Order,
    onGetOrdersByTableClick: (Int) -> Unit,
    basketState: MutableList<MenuItem>,
    currentCategory: MutableState<Category>,
    scaffoldState: ScaffoldState,
    currentTable: MutableState<Table>,
) {
    val state = viewModel.uiState.collectAsState()
    CafeTopBar(
        viewModel = viewModel,
        basketState = basketState,
        categories = state.value.categories,
        padding = padding,
        topBarState = topBarState,
        onGetOrdersByTableClick = onGetOrdersByTableClick,
        postMenuItems = postMenuItems,
        currentCategory = currentCategory,
        scaffoldState = scaffoldState,
        currentTable = currentTable
    )
}

@Composable
private fun CafeTopBar(
    categories: List<Category>,
    viewModel: MainActivityViewModel,
    padding: PaddingValues,
    topBarState: MutableState<Boolean> = mutableStateOf(value = false),
    onGetOrdersByTableClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier,
    postMenuItems: (OrderParams) -> by.ivan.CafeApp.ui.data.models.Order = {
        by.ivan.CafeApp.ui.data.models.Order(
            -1, OrderDetails(), -1, ""
        )
    },
    basketState: MutableList<MenuItem> = mutableListOf(),
    currentCategory: MutableState<Category> = mutableStateOf(Category()),
    currentTable: MutableState<Table> = mutableStateOf(Table()),
    scaffoldState: ScaffoldState,
) {
    val dialogState = remember {
        mutableStateOf(false)
    }

    val historyState = remember {
        mutableStateOf(false)
    }

    val coroutine = rememberCoroutineScope()

    if (dialogState.value) {
        BasketDialog(
            viewModel = viewModel,
            dialogState = dialogState,
            basketState = basketState,
            postMenuItems = postMenuItems,
            currentTable = currentTable,
        )
    }

    val table = viewModel.getDataStoreTable().getTable.collectAsState(initial = Table()).value!!
    if (historyState.value) {
        onGetOrdersByTableClick(table.id)
        HistoryDialog(
            viewModel = viewModel,
            historyState = historyState,
            currentCategory = currentCategory
        )
    }

    TopAppBar(
        modifier = modifier.statusBarsPadding()
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.CenterStart,
                ) {
                    IconButton(onClick = {
                        coroutine.launch {
                            scaffoldState.drawerState.open()
                        }
                        //  onNavigateToSearchTopBarClick()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                }
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    IconButton(onClick = {
                        topBarState.value = true
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = "Search",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = { dialogState.value = true }) {
                        Icon(
                            imageVector = Icons.Rounded.ShoppingCart,
                            contentDescription = "Basket",
                            tint = Color.White
                        )
                    }
                    IconButton(onClick = {
                        historyState.value = true
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_history_24),
                            contentDescription = "History of orders",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}