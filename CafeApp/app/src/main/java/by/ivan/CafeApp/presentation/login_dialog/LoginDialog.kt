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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.presentation.destinations.ChooseTableDialogDestination
import by.ivan.CafeApp.presentation.menu_screen.MenuItemsScreenViewModel
import by.ivan.CafeApp.ui.components.dialog_style.DefaultDialogStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = DefaultDialogStyle::class)
@Composable
fun LoginDialog(
    viewModel: MenuItemsScreenViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    LoginDialog(
        onNavigateToChooseTableDialogClick = { navigator.navigate(ChooseTableDialogDestination) },
        onDismissRequest = { navigator.popBackStack() }
    )
}

@Composable
private fun LoginDialog(
    onNavigateToChooseTableDialogClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    val pin = 1111 //TODO

    var text by remember {
        mutableStateOf("")
    }

    var isVisibleErrorText by remember {
        mutableStateOf(false)
    }

    var passwordVisible by remember { mutableStateOf(false) }

    Dialog(
        onDismissRequest = { onDismissRequest() },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Card(
            modifier = Modifier
                .size(
                    width = 350.dp,
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
                    value = text,
                    onValueChange = { text = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
                    singleLine = true,
                    visualTransformation =
                    if (passwordVisible) VisualTransformation.None
                    else PasswordVisualTransformation(),
                    placeholder = {
                        Text(
                            text = "Введите PIN",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    },
                )
                Text(
                    modifier = Modifier.alpha(if (isVisibleErrorText) 1f else 0f),
                    text = "Не правильный код",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Red
                )
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
                            if (text.isNotEmpty() && pin == text.toInt()) {
                                onDismissRequest()
                                onNavigateToChooseTableDialogClick()
                                isVisibleErrorText = false
                            } else {
                                isVisibleErrorText = true
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