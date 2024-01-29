package by.ivan.CafeApp.presentation.login_dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel

@Composable
fun LoginDialog(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    showChooseTableDialogClick: () -> Unit,
    onDismissRequest: () -> Unit,
) {
    LoginDialog(
        showChooseTableDialogClick = showChooseTableDialogClick,
        onDismissRequest = onDismissRequest,
    )
}

@Composable
private fun LoginDialog(
    showChooseTableDialogClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    val pin = 1111 //TODO

    val text = remember {
        mutableStateOf("")
    }

    val isVisibleErrorText = remember {
        mutableStateOf(false)
    }

    val passwordVisible = remember { mutableStateOf(false) }


    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = true,
            dismissOnClickOutside = false,
            dismissOnBackPress = true
        ),
    ) {
        Card(
            modifier = Modifier
                .size(
                    width = 400.dp,
                    height = 220.dp
                )
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Вход",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Black,
                )
                Text(
                    text = "PIN",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                )
                TextField(
                    value = text.value,
                    onValueChange = { text.value = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    visualTransformation =
                    if (passwordVisible.value) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    placeholder = {
                        Text(
                            text = "Введите PIN",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                )
                if (isVisibleErrorText.value) {
                    Text(
                        text = "Не правильный код",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Red
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                ) {
                    TextButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Отмена", style = MaterialTheme.typography.bodyMedium)
                    }
                    TextButton(
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onPrimary,
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        onClick = {
                            //onConfirmation
                            if (text.value.isNotEmpty() && pin == text.value.toInt()) {
                                onDismissRequest()
                                showChooseTableDialogClick()
                                isVisibleErrorText.value = false
                            } else {
                                isVisibleErrorText.value = true
                            }
                        },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Подтвердить", style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }
    }
}