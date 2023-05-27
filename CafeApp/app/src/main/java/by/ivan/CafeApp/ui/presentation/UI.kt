package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import by.ivan.CafeApp.ui.data.models.Category
import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.data.models.Table
import by.ivan.CafeApp.ui.presentation.ScreenIdentifiers.CATEGORIES_TOPBAR
import by.ivan.CafeApp.ui.presentation.ScreenIdentifiers.MAIN_SCREEN
import by.ivan.CafeApp.ui.presentation.ScreenIdentifiers.SEARCH_TOPBAR
import by.ivan.CafeApp.ui.presentation.theme.CafeAppTheme
import kotlinx.coroutines.launch

@Composable
fun UI(
    viewModel: MainActivityViewModel,
    onGetMenuItemsByCategoryClick: (Int) -> Unit,
) {
    CafeAppTheme {
        val topBarState = remember {
            mutableStateOf(false)
        }
        val scope = rememberCoroutineScope()

        val basketState = remember {
            mutableStateListOf<MenuItem>()
        }

        val currentCategory = remember {
            mutableStateOf(Category())
        }

        SideEffect() {
            scope.launch {
                viewModel.getCategories().join()
                currentCategory.value = viewModel.uiState.value.categories[0]
            }
        }

        val currentTable = remember {
            mutableStateOf(Table())
        }

        currentTable.value =
            viewModel.getDataStoreTable().getTable.collectAsState(initial = Table()).value!!

        val scaffoldState = rememberScaffoldState()

        val navController = rememberNavController()
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                when (topBarState.value) {
                    false -> CafeTopBar(
                        viewModel = viewModel,
                        basketState = basketState,
                        padding = PaddingValues.Absolute(2.dp),
                        onGetMenuItemsByCategoryClick = onGetMenuItemsByCategoryClick,
                        topBarState = topBarState,
                        onNavigateToSearchTopBarClick = { navController.navigate(SEARCH_TOPBAR) },
                        postMenuItems = { viewModel.postMenuItems(it) },
                        onGetOrdersByTableClick = { viewModel.getOrdersByTable(it) },
                        currentCategory = currentCategory,
                        currentTable = currentTable,
                        scaffoldState = scaffoldState
                    )
                    true -> SearchTopBar(
                        navController = navController,
                        onMenuItemTitleChange = { viewModel.getMenuItemsByTitle(it) },
                        topBarState = topBarState,
                        onNavigateToCategoriesTopBarClick = {
                            navController.navigate(
                                CATEGORIES_TOPBAR
                            )
                        },
                        currentCategory = currentCategory,
                        onGetMenuItemsByCategoryClick = { viewModel.getMenuItemsByCategory(it) }
                    )
                }
            },
            drawerContent = {
                Drawer(
                    viewModel = viewModel,
                    currentTable = currentTable
                )
            }
        ) { padding ->
            NavHost(navController = navController, startDestination = MAIN_SCREEN) {
                composable(route = MAIN_SCREEN) {
                    MenuItemsScreen(
                        viewModel = viewModel,
                        padding = PaddingValues(2.dp),
                        navController = navController,
                        basketState = basketState,
                        getMenuItemsSortedByAlphabet = { viewModel.getMenuItemsSortedByAlphabet(it) },
                        getMenuItemsSortedByPrice = { viewModel.getMenuItemsSortedByPrice(it) },
                        onGetMenuItemsByCategoryClick = { viewModel.getMenuItemsByCategory(it) },
                        currentCategory = currentCategory
                    )
                }
            }
        }
    }
}