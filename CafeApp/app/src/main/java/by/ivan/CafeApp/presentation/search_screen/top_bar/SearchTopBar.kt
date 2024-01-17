package by.ivan.CafeApp.presentation.search_screen.top_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.search_screen.SearchItemsScreenViewModel

@Composable
fun SearchTopBar(
    viewModel: SearchItemsScreenViewModel = hiltViewModel(),
    menuItemTitleForSearch: String,
    onGetMenuItemsByTitleUpdate: () -> Unit,
    onUpdateMenuItemTitleInput: (String) -> Unit,
    onPopBackStackClick: () -> Unit
) {
    SearchTopBar(
        menuItemTitleForSearch = menuItemTitleForSearch,
        onGetMenuItemsByTitleUpdate = onGetMenuItemsByTitleUpdate,
        onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
        onPopBackStackClick = onPopBackStackClick
    )
}

@Composable
private fun SearchTopBar(
    menuItemTitleForSearch: String = "",
    onGetMenuItemsByTitleUpdate: () -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit = {},
    onPopBackStackClick: () -> Unit = {}
) {
    TopAppBar(backgroundColor = Color.White) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                onPopBackStackClick()
            }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black,
                )
            }
            TextField(
                value = menuItemTitleForSearch,
                onValueChange = { value ->
                    onUpdateMenuItemTitleInput(value)
                    onGetMenuItemsByTitleUpdate()
                },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(color = Color.Black, fontSize = 20.sp),
                placeholder = { Text(text = "Введите название блюда") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            )
        }
    }
}