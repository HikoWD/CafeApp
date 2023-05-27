package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.ui.data.models.Table

@Composable
fun LoginDialog(
    viewModel: MainActivityViewModel = hiltViewModel(),
    loginDialogState: MutableState<Boolean>,
    currentTable: MutableState<Table>,
    x: Int = 0 //????? - xdd
) {
    LoginDialog(viewModel = viewModel, loginDialogState = loginDialogState, currentTable = currentTable)
}

@Composable
private fun LoginDialog(
    viewModel: MainActivityViewModel,
    loginDialogState: MutableState<Boolean> = mutableStateOf(false),
    currentTable: MutableState<Table> = mutableStateOf(Table())
) {
    val pin = 1111 //TODO
    val text = remember {
        mutableStateOf("")
    }
    val isVisibleErrorText = remember {
        mutableStateOf(false)
    }
    val chooseTableDialog = remember {
        mutableStateOf(false)
    }

    if (chooseTableDialog.value) {
        ChooseTableDialog(
            viewModel = viewModel,
            chooseTableDialog = chooseTableDialog,
            currentTable = currentTable,
            loginDialogState = loginDialogState
        )
    }

    val passwordVisible = remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { loginDialogState.value = false },
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
    ) {
        Card(modifier = Modifier.size(height = 210.dp, width = 250.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(text = "Вход", fontSize = 18.sp, fontWeight = FontWeight.Black)
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                        IconButton(onClick = {
                            loginDialogState.value = false
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close dialog",
                                tint = Color.Black
                            )
                        }
                    }
                }
                Text(text = "PIN")
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    visualTransformation =
                    if (passwordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    placeholder = { Text(text = "Введите PIN") },
                )
                if (isVisibleErrorText.value) {
                    Text(text = "Не правильный код", color = Color.Red)
                }
                Button(onClick = {
                    if (text.value.isNotEmpty() && pin == text.value.toInt()) {
                        chooseTableDialog.value = true
                        isVisibleErrorText.value = false
                    } else {
                        isVisibleErrorText.value = true
                    }
                }) {
                    Text(text = "Подтвердить")
                }
            }
        }
    }
}