package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item

@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val items = viewModel.sortedItemList
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState
    ){ ->
        items(
            items = items.value,
            key = { item: Item -> item.id}
        ) { item ->

            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Text(
                    text = item.id.toString()
                )
                Text(
                    text = item.listId.toString()
                )
                Text(
                    text = item.name ?: ""
                )

            }
            Spacer(
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }

    }
}