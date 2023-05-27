package by.ivan.CafeApp.ui.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.ui.data.models.Table
import kotlinx.coroutines.launch

@Composable
fun ChooseTableDialog(
    viewModel: MainActivityViewModel = hiltViewModel(),
    chooseTableDialog: MutableState<Boolean>,
    loginDialogState: MutableState<Boolean>,
    currentTable: MutableState<Table>
) {
    val state = viewModel.uiState.collectAsState()
    ChooseTableDialog(
        viewModel = viewModel,
        tables = state.value.tables,
        tableDialog = chooseTableDialog,
        loginDialogState = loginDialogState
    )
}

@Composable
private fun ChooseTableDialog(
    viewModel: MainActivityViewModel,
    tables: List<Table>,
    tableDialog: MutableState<Boolean>,
    loginDialogState: MutableState<Boolean> = mutableStateOf(false),
    currentTable: MutableState<Table> = mutableStateOf(Table()),
) {
    val scope = rememberCoroutineScope()
    val table = viewModel.getDataStoreTable().getTable.collectAsState(initial = Table()).value!!
    Dialog(
        onDismissRequest = { tableDialog.value = false },
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        )
    ) {
        Card(modifier = Modifier.size(height = 500.dp, width = 550.dp)) {
            Column(modifier = Modifier.fillMaxSize()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                        Text(
                            text = "Выберете столик",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
                        IconButton(onClick = {
                            tableDialog.value = false
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(4.dp)
                ) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box(contentAlignment = Alignment.TopStart) {
                            Text(
                                modifier = Modifier.background(Color.Green),
                                text = "   ",
                            )
                        }
                        Box() {
                            Text(
                                text = " - текущий столик",
                                fontSize = 18.sp
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Box() {
                            Text(
                                modifier = Modifier.background(Color.Red),
                                text = "   ",
                            )
                        }
                        Box(modifier = Modifier.fillMaxWidth()) {
                            Text(
                                text = " - свободные столики",
                                fontSize = 18.sp
                            )
                        }
                    }
                }
                LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(7.5f),
                    columns = GridCells.Adaptive(minSize = 100.dp),
                    contentPadding = PaddingValues(
                        start = 12.dp,
                        top = 16.dp,
                        end = 12.dp,
                        bottom = 16.dp
                    ),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(items = tables) { index, item ->
                        if (table.id == item.id) {
                            Box(
                                modifier = Modifier
                                    .size(height = 70.dp, width = 120.dp)
                                    .border(
                                        border = BorderStroke(2.dp, Color.Black),
                                        shape = RoundedCornerShape(2.dp)
                                    )
                                    .background(Color.Green),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Столик №${item.title}", fontSize = 16.sp)
                            }
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(height = 70.dp, width = 120.dp)
                                    .border(
                                        width = 4.dp,
                                        color = Gray,
                                        shape = RoundedCornerShape(16.dp)
                                    )
                                    .background(Color.Red)
                                    .clickable {
                                        // currentTable.value = item
                                        scope.launch {
                                            viewModel
                                                .getDataStoreTable()
                                                .saveTable(item)
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "Столик №${item.title}", fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
        }
    }
}
