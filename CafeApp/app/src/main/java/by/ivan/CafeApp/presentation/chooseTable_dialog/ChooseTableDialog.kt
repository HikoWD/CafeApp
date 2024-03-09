package by.ivan.CafeApp.presentation.chooseTable_dialog

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import by.ivan.CafeApp.domain.table.model.Table
import by.ivan.CafeApp.presentation.chooseTable_dialog.main.ChooseTableDialogMain
import by.ivan.CafeApp.ui.components.dialog_style.DefaultDialogStyle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination(style = DefaultDialogStyle::class)
@Composable
fun ChooseTableDialog(
    viewModel: ChooseTableDialogViewModel = hiltViewModel(),
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    localContext: Context = LocalContext.current,
    navigator: DestinationsNavigator,
) {
    val state by viewModel.uiState.collectAsState()

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.getTables()
                viewModel.getCurrentTable()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ChooseTableDialog(
        localContext = localContext,
        tables = state.tables,
        currentTable = state.currentTable,
        tableDialogState = state.tableDialogState,
        onSaveTableClick = { viewModel.saveTable(it) },
        onDismissRequest = { navigator.popBackStack() },
    )
}

@Composable
private fun ChooseTableDialog(
    localContext: Context,
    tables: List<Table> = listOf(),
    currentTable: Table = Table(),
    tableDialogState: TableDialogState = TableDialogState.Idle,
    onSaveTableClick: (Table) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
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
                    width = 400.dp,
                    height = 550.dp
                )
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier
                            .weight(2f)
                            .padding(top = 8.dp),
                        text = "Выберете столик",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp,
                        lineHeight = 50.sp,
                        maxLines = 1
                    )
                    Box(
                        modifier = Modifier.weight(0.5f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        IconButton(onClick = {
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

                Crossfade(
                    targetState = tableDialogState,
                    modifier = Modifier
                        .weight(9f),
                    animationSpec = tween(
                        durationMillis = 400,
                        easing = LinearEasing
                    ),
                    label = ""
                ) { state ->
                    when (state) {
                        is TableDialogState.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize(),
                            ) {
                                CircularProgressIndicator(
                                    color = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.align(Alignment.Center)
                                )
                            }
                        }

                        is TableDialogState.Error -> {
                            Toast.makeText(localContext, "${state.errorMessage}", Toast.LENGTH_LONG)
                                .show()
                        }

                        is TableDialogState.Loaded -> {
                            ChooseTableDialogMain(
                                tables = tables,
                                currentTable = currentTable,
                                onSaveTableClick = onSaveTableClick
                            )
                        }

                        is TableDialogState.Idle -> {}

                        else -> {}
                    }
                }
            }
        }
    }
}