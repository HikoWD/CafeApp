package by.ivan.CafeApp.presentation.chooseTable_dialog.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import by.ivan.CafeApp.domain.table.model.Table

@Composable
fun ChooseTableDialogMain(
    tables: List<Table>,
    currentTable: Table = Table(),
    onSaveTableClick: (Table) -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
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
                    style = MaterialTheme.typography.bodyLarge
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    modifier = Modifier.background(Color.Red),
                    text = "   ",
                    textAlign = TextAlign.Left
                )
                Text(
                    text = " - свободные столики",
                    style = MaterialTheme.typography.bodyLarge
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
                        Text(
                            text = "Столик №${item.title}",
                            style = MaterialTheme.typography.bodyMedium
                        )
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
                        Text(
                            text = "Столик №${item.title}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}