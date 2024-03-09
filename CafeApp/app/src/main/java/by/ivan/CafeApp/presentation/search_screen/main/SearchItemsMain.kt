package by.ivan.CafeApp.presentation.search_screen.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import by.ivan.CafeApp.domain.search_history.model.SearchHistoryItem
import by.ivan.CafeApp.presentation.search_screen.SearchItemsScreenViewModel

@Composable
fun SearchItemsMain(
    viewModel: SearchItemsScreenViewModel = hiltViewModel(),
    searchHistoryItems: List<SearchHistoryItem>,
    paddingValuesParent: PaddingValues,
    paddingValuesChild: PaddingValues,
    showSearchBar: (Boolean) -> Unit,
    onUpdateMenuItemTitleInput: (String) -> Unit
) {
    SearchItemsMain(
        searchHistoryItems = searchHistoryItems,
        paddingValuesParent = paddingValuesParent,
        paddingValuesChild = paddingValuesChild,
        onUpdateMenuItemTitleInput = onUpdateMenuItemTitleInput,
        showSearchBar = showSearchBar
    )
}

@Composable
private fun SearchItemsMain(
    searchHistoryItems: List<SearchHistoryItem> = listOf(),
    paddingValuesParent: PaddingValues = PaddingValues(2.dp),
    paddingValuesChild: PaddingValues = PaddingValues(2.dp),
    showSearchBar: (Boolean) -> Unit = {},
    onUpdateMenuItemTitleInput: (String) -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                top = paddingValuesChild.calculateTopPadding(),
                bottom = paddingValuesParent.calculateBottomPadding()
            )
    ) {
        itemsIndexed(items = searchHistoryItems) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp, vertical = 5.dp)
                    .shadow(
                        elevation = 10.dp,
                        clip = true,
                        shape = RoundedCornerShape(20.dp)
                    )
                    .clickable {
                        onUpdateMenuItemTitleInput(item.query)
                        showSearchBar(true)
                    },
                shape = RoundedCornerShape(20.dp),
                elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background,
                ),
                border = BorderStroke(1.dp, Color.Black),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 8.dp,
                            top = 4.dp,
                            end = 8.dp,
                            bottom = 4.dp
                        )
                ) {
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.query,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        modifier = Modifier.weight(1f),
                        text = item.timestamp,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Black,
                        textAlign = TextAlign.End
                    )
                }
            }
        }
    }
}