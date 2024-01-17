package by.ivan.CafeApp.presentation.chooseTable_dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.table.model.Table
import kotlinx.coroutines.Job

@Composable
fun ChooseTableDialog(
    viewModel: ChooseTableDialogViewModel = hiltViewModel(),
    tables: List<Table>,
    currentTable: Table,
    onGetTablesEffect: () -> Job,
    onGetCurrentTableEffect: () -> Job,
    onSaveTableClick: (Table) -> Unit,
    onDismissRequest: () -> Unit,
) {
    DisposableEffect(Unit) {
        val job1 = onGetTablesEffect()
        val job = onGetCurrentTableEffect()
        onDispose {
            job1.cancel()
            job.cancel()
        }
    }

    ChooseTableDialog(
        tables = tables,
        currentTable = currentTable,
        onSaveTableClick = onSaveTableClick,
        onDismissRequest = onDismissRequest,
    )
}

@Composable
private fun ChooseTableDialog(
    tables: List<Table>,
    currentTable: Table = Table(),
    onSaveTableClick: (Table) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        },
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
                    Text(
                        modifier = Modifier.weight(1f),
                        text = "Выберете столик",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Center
                    )
                    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.CenterEnd) {
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
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.5f)
                        .padding(4.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        Text(
                            modifier = Modifier.background(Color.Green),
                            text = "   ",
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = " - текущий столик",
                            fontSize = 18.sp
                        )
                    }
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.background(Color.Red),
                            text = "   ",
                            textAlign = TextAlign.Left
                        )
                        Text(
                            text = " - свободные столики",
                            fontSize = 18.sp
                        )
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
                        if (currentTable.id == item.id) {
                            Box(
                                modifier = Modifier
                                    .size(height = 70.dp, width = 120.dp)
                                    .border(
                                        border = BorderStroke(2.dp, Color.Black),
                                        shape = RectangleShape
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
                                        border = BorderStroke(2.dp, Color.Black),
                                        shape = RectangleShape
                                    )
                                    .background(Color.Red)
                                    .clickable {
                                        onSaveTableClick(item)
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