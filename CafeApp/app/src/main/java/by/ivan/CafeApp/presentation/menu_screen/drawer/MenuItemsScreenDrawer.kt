package by.ivan.CafeApp.presentation.menu_screen.drawer

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.chooseTable_dialog.ChooseTableDialog
import by.ivan.CafeApp.presentation.chooseTable_dialog.ChooseTableDialogViewModel
import by.ivan.CafeApp.presentation.login_dialog.LoginDialog
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel

@Composable
fun MenuItemsScreenDrawer(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    chooseTableDialogViewModel: ChooseTableDialogViewModel = hiltViewModel(),
    showLoginDialogState: Boolean,
    showChooseTableDialogState: Boolean,
    onShowLoginDialogClick: () -> Unit,
    onShowChooseTableDialogClick: () -> Unit,
    onLoginDialogDismissRequest: () -> Unit,
    onChooseTableDialogDismissRequest: () -> Unit,
    onDrawerCloseEffect: () -> Unit,
) {
    val state by chooseTableDialogViewModel.uiState.collectAsState()

    if (showLoginDialogState) {
        LoginDialog(
            showChooseTableDialogClick = onShowChooseTableDialogClick,
            onDismissRequest = onLoginDialogDismissRequest
        )
    }

    if (showChooseTableDialogState) {
        ChooseTableDialog(
            viewModel = chooseTableDialogViewModel,
            tables = state.tables,
            currentTable = state.currentTable,
            onGetTablesEffect = { chooseTableDialogViewModel.getTables() },
            onGetCurrentTableEffect = { chooseTableDialogViewModel.getCurrentTable() },
            onSaveTableClick = { chooseTableDialogViewModel.saveTable(table = it) },
            onDismissRequest = onChooseTableDialogDismissRequest
        )
    }

    MenuItemsScreenDrawer(
        showLoginDialogClick = onShowLoginDialogClick,
        onDrawerCloseSwipe = onDrawerCloseEffect
    )
}

@Composable
private fun MenuItemsScreenDrawer(
    showLoginDialogClick: () -> Unit = {},
    onDrawerCloseSwipe: () -> Unit = {},
) {
    Column(modifier = Modifier
        .fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(9f),
            contentAlignment = Alignment.Center
        ) {
            Button(modifier = Modifier
                .width(250.dp)
                .height(100.dp),
                border = BorderStroke(1.dp, Color.Black),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Black),
                onClick = {
                    onDrawerCloseSwipe()
                    showLoginDialogClick()
                    // navigator.navigate(LoginDialogDestination())
                }) {
                Text(text = "Login", fontSize = 24.sp)
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            contentAlignment = Alignment.BottomCenter
        ) {
            Text(text = "Ivan Bakinouski", fontSize = 12.sp, fontWeight = FontWeight.Black)
        }
    }
}