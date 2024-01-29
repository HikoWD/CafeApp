package by.ivan.CafeApp.presentation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import by.ivan.CafeApp.R
import by.ivan.CafeApp.presentation.chooseTable_dialog.ChooseTableDialog
import by.ivan.CafeApp.presentation.login_dialog.LoginDialog
import by.ivan.CafeApp.ui.theme.CafeAppTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import kotlinx.coroutines.launch

@OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
@Composable
fun UI(
    navController: NavHostController = rememberAnimatedNavController(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
) {
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        //default `rootDefaultAnimations` means no animations
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING,
        defaultAnimationsForNestedNavGraph = mapOf(
            NavGraphs.root to NestedNavGraphDefaultAnimations(
                enterTransition = { fadeIn(animationSpec = tween(1500)) },
                exitTransition = { fadeOut(animationSpec = tween(1500)) }
            )
            //NavGraphs.otherNestedGraph to NestedNavGraphDefaultAnimations.ACCOMPANIST_FADING
        ) // all other nav graphs not specified in this map, will get their animations from the `rootDefaultAnimations` above.
    )

    val showLoginDialog = remember { mutableStateOf(false) }
    val showChooseTableDialog = remember { mutableStateOf(false) }

    val coroutineScope = rememberCoroutineScope()

    if (showLoginDialog.value) {
        LoginDialog(
            showChooseTableDialogClick = { showChooseTableDialog.value = true },
            onDismissRequest = { showLoginDialog.value = false }
        )
    }

    if (showChooseTableDialog.value) {
        ChooseTableDialog(
            onDismissRequest = {
                showChooseTableDialog.value = false
            }
        )
    }

    val items = listOf(
        DrawerItem.LoginButton(onClick = { showLoginDialog.value = true })
    )

    CafeAppTheme {
        Surface {
            ModalNavigationDrawer(
                drawerState = drawerState,
                drawerContent = {
                    AppDrawerContent(
                        drawerState = drawerState,
                        drawerItems = items
                    )
                },
                content = {
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        bottomBar = {
                            MainActivityBottomBar(
                                navController = navController
                            )
                        },
                        content = { paddingValues ->
                            DestinationsNavHost(
                                navController = navController,
                                navGraph = NavGraphs.root,
                                dependenciesContainerBuilder = {
                                    dependency(NavGraphs.root) {
                                        paddingValues
                                    }
                                    dependency(NavGraphs.root) {
                                        //onMenuButtonClick: () -> Unit
                                        {
                                            coroutineScope.launch {
                                                drawerState.apply {
                                                    if (isClosed) open() else close()
                                                }
                                            }
                                        }
                                    }
                                },
                                engine = navHostEngine
                            )
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun AppDrawerContent(
    drawerState: DrawerState,
    drawerItems: List<DrawerItem>
) {
    val coroutineScope = rememberCoroutineScope()
    ModalDrawerSheet {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(9.5f)
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    itemsIndexed(items = drawerItems) { index, item ->
                        AppDrawerItem(
                            item = item,
                            onClick = {
                                coroutineScope.launch {
                                    drawerState.close()
                                }
                                when (item) {
                                    is DrawerItem.LoginButton -> {
                                        item.onClick()
                                    }
                                }
                            }
                        )
                    }
                }
                Text(
                    modifier = Modifier
                        .weight(0.5f),
                    text = "Ivan Bakinouski",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.ExtraLight,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun AppDrawerItem(
    item: DrawerItem,
    onClick: () -> Unit
) {
    Surface(
        color = MaterialTheme.colorScheme.onPrimary,
        modifier = Modifier
            .width(200.dp)
            .padding(16.dp),
        onClick = { onClick() },
        shape = RoundedCornerShape(50),
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = stringResource(id = item.description),
                modifier = Modifier
                    .size(24.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(id = item.title),
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
            )
        }
    }
}

sealed class DrawerItem(
    @StringRes val title: Int,
    @DrawableRes val icon: Int,
    @StringRes val description: Int
) {
    data class LoginButton(val onClick: () -> Unit) :
        DrawerItem(
            title = R.string.drawer_login,
            icon = R.drawable.baseline_login_24,
            description = R.string.drawer_login_description
        )
}