package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val groupedItems = viewModel.groupedItems.value
    val listState = rememberLazyListState()

    LazyColumn(state = listState){ ->


        groupedItems.forEach { (listId, itemEntry) ->
            stickyHeader { ->
                ListIdHeader(listId, Modifier.fillParentMaxWidth())
            }
            items(itemEntry) { item ->
                ListItem(item = item)
            }
        }
    }
}

@Composable
fun ListIdHeader(
    listId: Int,
    modifier: Modifier
) {
    Text(
        modifier = modifier,
        text = listId.toString()
    )
}

@Composable
fun ListItem(
    item: Item
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(text = item.id.toString())
        Text(text = item.listId.toString())
        item.name?.let { Text(text = it) }
    }
}