package by.ivan.CafeApp.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun UI() {
    val navController: NavHostController = rememberAnimatedNavController()

    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING, //default `rootDefaultAnimations` means no animations
        defaultAnimationsForNestedNavGraph = mapOf(
            NavGraphs.root to NestedNavGraphDefaultAnimations(
                enterTransition = { fadeIn(animationSpec = tween(1500)) },
                exitTransition = { fadeOut(animationSpec = tween(1500)) }
            )
            //NavGraphs.otherNestedGraph to NestedNavGraphDefaultAnimations.ACCOMPANIST_FADING
        ) // all other nav graphs not specified in this map, will get their animations from the `rootDefaultAnimations` above.
    )


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainActivityBottomBar(
                navController = navController
            )
        }
    ) { paddingValues ->
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            dependenciesContainerBuilder = {
                dependency(NavGraphs.root) {
                    paddingValues

                }
            },
            engine = navHostEngine
        )
        //   {
//            composable(MenuItemsScreenDestination) {
//                val menuItemsScreenViewModel: MenuItemsScreenViewModel = hiltViewModel()
//                MenuItemsScreen(
//                    viewModel = menuItemsScreenViewModel,
//                    navigator = destinationsNavigator,
//                    onGetMenuItemsByCategoryIdClick = {
//                        menuItemsScreenViewModel.getMenuItemsByCategoryId(
//                            categoryId = it
//                        )
//                    },
//                    onGetMenuItemsSortedByAlphabetClick = {
//                        menuItemsScreenViewModel.getMenuItemsSortedByAlphabet(
//                            categoryId = it
//                        )
//                    },
//                    onGetMenuItemsSortedByPriceClick = {
//                        menuItemsScreenViewModel.getMenuItemsSortedByPrice(
//                            categoryId = it
//                        )
//                    },
//                    onAddMenuItemToCartClick = {
//                        menuItemsScreenViewModel.addCartItem(menuItem = it)
//                    },
////                    onDrawerOpenClick = {
////                        onDrawerOpenClick(scaffoldState = scaffoldState)
////                    }
//                )
//            }

//            composable(SearchItemsScreenDestination) {
//                val searchItemsScreenViewModel: SearchItemsScreenViewModel = hiltViewModel()
//                SearchItemsScreen(
//                    viewModel = searchItemsScreenViewModel,
//                    onGetMenuItemsByTitleInput = {
//                        searchItemsScreenViewModel.getMenuItemsByTitle(
//                            title = it
//                        )
//                    },
//                    onPopBackStackClick = { navController.popBackStack() },
//                    onAddMenuItemToCartClick = { searchItemsScreenViewModel.addCartItem(menuItem = it) }
//                )
//            }

//            composable(HistoryOrdersScreenDestination) {
//                val historyOrdersScreenViewModel: HistoryOrdersScreenViewModel = hiltViewModel()
//                HistoryOrdersScreen(
//                    viewModel = historyOrdersScreenViewModel,
//                    onNavigateToOrderDetailsScreenClick = {
//                        navController.navigate(
//                            OrderDetailsScreenDestination(it)
//                        )
//                    }
//                )
//            }

//            composable(CartScreenDestination) {
//                val cartScreenViewModel: CartScreenViewModel = hiltViewModel()
//                CartScreen(
//                    viewModel = cartScreenViewModel,
//                    onGetCartItemsEffect = { cartScreenViewModel.getCartItems() },
//                    onAddMenuItemToCartClick = { cartScreenViewModel.addCartItem(menuItem = it) },
//                    onRemoveMenuItemFromCartClick = {
//                        cartScreenViewModel.decreaseCountCartItem(
//                            menuItem = it
//                        )
//                    },
//                    onRemoveAllMenuItemsFromCartClick = { cartScreenViewModel.removeCartItems() },
//                    onPostOrderClick = { cartScreenViewModel.postOrder() },
//                    paddingValues = it
//                )
//            }

//            composable(OrderDetailsScreenDestination) {
//                val orderDetailsScreenViewModel: OrderDetailsScreenViewModel = hiltViewModel()
//                OrderDetailsScreen(
//                    viewModel = orderDetailsScreenViewModel,
//                    navigator = destinationsNavigator
//                )
//            }

//            composable(ChooseTableDialogDestination) {
//                val chooseTableDialogViewModel: ChooseTableDialogViewModel = hiltViewModel()
//                by.ivan.CafeApp.ui.presentation.chooseTable_dialog.ChooseTableDialog(
//                    viewModel = chooseTableDialogViewModel,
//                    navigator = destinationsNavigator
//                )
//            }
        // }
    }
}