package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import by.ivan.CafeApp.ui.data.models.Category
import by.ivan.CafeApp.ui.data.models.MenuItem
import by.ivan.CafeApp.ui.presentation.ScreenIdentifiers.MAIN_SCREEN

@Composable
fun SearchTopBar(
    viewModel: MainActivityViewModel = hiltViewModel(),
    navController: NavController,
    onMenuItemTitleChange: (String) -> Unit,
    topBarState: MutableState<Boolean>,
    onNavigateToCategoriesTopBarClick: () -> Unit,
    currentCategory: MutableState<Category>,
    onGetMenuItemsByCategoryClick: (Int) -> Unit,
) {
    val state = viewModel.uiState.collectAsState()
    SearchTopBar(
        menuItems = state.value.menuItems,
        viewModel = viewModel,
        navController = navController,
        onMenuItemTitleChange = onMenuItemTitleChange,
        topBarState = topBarState,
        onNavigateToCategoriesTopBarClick = onNavigateToCategoriesTopBarClick,
        currentCategory = currentCategory,
        onGetMenuItemsByCategoryClick = onGetMenuItemsByCategoryClick,
    )
}

@Composable
private fun SearchTopBar(
    menuItems: List<MenuItem>,
    viewModel: MainActivityViewModel,
    navController: NavController,
    onMenuItemTitleChange: (String) -> Unit = {},
    topBarState: MutableState<Boolean> = mutableStateOf(true),
    onNavigateToCategoriesTopBarClick: () -> Unit = {},
    currentCategory: MutableState<Category> = mutableStateOf(Category()),
    onGetMenuItemsByCategoryClick: (Int) -> Unit = {},
) {
    var menuItemTitle: String by remember {
        mutableStateOf("")
    }

    TopAppBar(backgroundColor = Color.White) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                topBarState.value = false
                onGetMenuItemsByCategoryClick(currentCategory.value.id)
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back to categories top bar",
                    tint = Color.Black,
                )
            }
            TextField(
                value = menuItemTitle,
                onValueChange = { value ->
                    menuItemTitle = value
                    onMenuItemTitleChange(menuItemTitle)
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                placeholder = { Text(text = "Введите название блюда") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
        }
    }
}