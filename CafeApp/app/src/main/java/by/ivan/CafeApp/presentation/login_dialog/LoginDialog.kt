package by.ivan.CafeApp.presentation.login_dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel

//@MenuNavGraph
//@Destination
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
        Card(modifier = Modifier.size(height = 210.dp, width = 250.dp)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Вход",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = {
                            //navigator.navigate(NavGraphs.root)
                            onDismissRequest()
                        }) {
                            Icon(
                                imageVector = Icons.Filled.Close,
                                contentDescription = "Close dialog",
                                tint = Color.Black
                            )
                        }
                    }
                }
                Text(text = "PIN", textAlign = TextAlign.Left)
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
                        onDismissRequest()
                        showChooseTableDialogClick()
                        //navigator.navigate(ChooseTableDialogDestination())
                        isVisibleErrorText.value = false
                    } else {
                        isVisibleErrorText.value = true
                    }
                }) {
                    Text(text = "Подтвердить", textAlign = TextAlign.Left)
                }
            }
        }

    }
}