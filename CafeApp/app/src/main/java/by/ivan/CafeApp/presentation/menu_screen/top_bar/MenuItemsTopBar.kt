package by.ivan.CafeApp.presentation.menu_screen.top_bar

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.R
import by.ivan.CafeApp.presentation.menu_screen.CategoriesScreenState
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel
import by.ivan.CafeApp.presentation.menu_screen.model.CategoryUi

@Composable
fun MenuItemsTopBar(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    categories: List<CategoryUi>,
    categoriesScreenState: CategoriesScreenState,
    onSelectCategoryClick: (CategoryUi) -> Unit,
    onGetMenuItemsSortedByAlphabetClick: () -> Unit,
    onGetMenuItemsSortedByPriceClick: () -> Unit,
    onMenuButtonClick: () -> Unit
) {
    val localContext: Context = LocalContext.current
    MenuItemsTopBar(
        localContext = localContext,
        categories = categories,
        categoriesScreenState = categoriesScreenState,
        onSelectCategoryClick = onSelectCategoryClick,
        onGetMenuItemsSortedByAlphabetClick = onGetMenuItemsSortedByAlphabetClick,
        onGetMenuItemsSortedByPriceClick = onGetMenuItemsSortedByPriceClick,
        onMenuButtonClick = onMenuButtonClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MenuItemsTopBar(
    localContext: Context = LocalContext.current,
    categories: List<CategoryUi> = listOf(),
    categoriesScreenState: CategoriesScreenState = CategoriesScreenState.Idle,
    onSelectCategoryClick: (CategoryUi) -> Unit = {},
    onGetMenuItemsSortedByAlphabetClick: () -> Unit = {},
    onGetMenuItemsSortedByPriceClick: () -> Unit = {},
    onMenuButtonClick: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        TopAppBar(
            modifier = Modifier
                .fillMaxWidth(),
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
            ),
            navigationIcon = {
                IconButton(onClick = {
                    onMenuButtonClick()
                }) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = stringResource(id = R.string.drawer_menu_description)
                    )
                }
            },
            actions = {
                IconButton(onClick = {
                    onGetMenuItemsSortedByAlphabetClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_sort_by_alpha_24),
                        contentDescription = "Sort by alphabet",
                        tint = Color.White
                    )
                }
                IconButton(onClick = {
                    onGetMenuItemsSortedByPriceClick()
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_attach_money_24),
                        contentDescription = "Sort by price",
                        tint = Color.White
                    )
                }
            },
            title = {}
        )

        if (categories.isNotEmpty()) {
            CategoriesState(
                localContext = localContext,
                categories = categories,
                categoriesScreenState = categoriesScreenState,
                onSelectCategoryClick = onSelectCategoryClick
            )
        }
    }
}

@Composable
fun CategoriesState(
    localContext: Context = LocalContext.current,
    categories: List<CategoryUi>,
    categoriesScreenState: CategoriesScreenState,
    onSelectCategoryClick: (CategoryUi) -> Unit
) {
    Crossfade(
        targetState = categoriesScreenState,
        modifier = Modifier
            .fillMaxWidth(),
        animationSpec = tween(
            durationMillis = 400,
            easing = LinearEasing
        ),
        label = ""
    ) { state ->
        when (state) {
            is CategoriesScreenState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                ) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            is CategoriesScreenState.Error -> {
                Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG).show()
                DrawCategories(
                    categories = categories,
                    onSelectCategoryClick = onSelectCategoryClick,
                )
            }

            is CategoriesScreenState.Loaded -> {
                DrawCategories(
                    categories = categories,
                    onSelectCategoryClick = onSelectCategoryClick,
                )
            }

            is CategoriesScreenState.Idle -> {}

            else -> {}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawCategories(
    categories: List<CategoryUi>,
    onSelectCategoryClick: (CategoryUi) -> Unit
) {
    PrimaryScrollableTabRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        selectedTabIndex = categories.indexOf(categories.find { it.selected }),
        edgePadding = 0.dp,
        divider = { HorizontalDivider(thickness = 1.dp, color = Color.LightGray) }
    ) {
        categories.forEachIndexed { index, categoryUi ->
            Tab(
                selected = categoryUi.selected,
                onClick = { onSelectCategoryClick(categoryUi) }) {
                Text(
                    text = categoryUi.title.uppercase(),
                    modifier = Modifier
                        .padding(8.dp),
                    color = if (categoryUi.selected) Color.Black else Color.DarkGray,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}