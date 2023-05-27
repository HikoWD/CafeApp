package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.ui.data.models.Table

@Composable
fun Drawer(
    viewModel: MainActivityViewModel = hiltViewModel(),
    currentTable: MutableState<Table>,
    x: Int = 0 //????? - xdd
) {
    Drawer(viewModel = viewModel, currentTable = currentTable)
}

@Composable
private fun Drawer(
    viewModel: MainActivityViewModel,
    currentTable: MutableState<Table> = mutableStateOf(Table())
) {
    val loginDialogState = remember {
        mutableStateOf(false)
    }

    if (loginDialogState.value) {
        LoginDialog(
            viewModel = viewModel,
            loginDialogState = loginDialogState,
            currentTable = currentTable
        )
    }

    Column(modifier = Modifier.fillMaxSize()) {
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
                    loginDialogState.value = true
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