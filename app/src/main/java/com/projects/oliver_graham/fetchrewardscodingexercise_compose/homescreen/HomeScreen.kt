package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.ExpandableRow

@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val groupedItems = viewModel.groupedItems.value
    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxHeight()
    ) {

        items(groupedItems.toList()) { (listId, itemList) ->
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) { ->
                ExpandableRow(listId, itemList)

            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }

    }
    /*LazyColumn(
        state = listState,
        contentPadding = PaddingValues(bottom = 80.dp)
    ){ ->

        // groupedItems is a map;
        // listId the key to the list of corresponding items
        groupedItems.forEach { (listId, itemList) ->
            stickyHeader { ->
                ListIdHeader(listId, Modifier.fillParentMaxWidth())
            }
            items(itemList) { item ->
                ListItem(item = item)
            }
        }
    }*/
}

@Composable
fun ListIdHeader(
    listId: Int,
    modifier: Modifier
) {
   /* Text(
        modifier = modifier.background(color = MaterialTheme.colors.secondary),
        text = ("List Id: $listId")
    )*/
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp),
        contentColor = MaterialTheme.colors.primary,
        elevation = 16.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) { ->

            Text(text = "ID")
            Text(text = "List ID group $listId")
            Text(text = "Name")
        }
    }
}

@Composable
fun ListItem(
    item: Item
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 2.dp),
        contentColor = MaterialTheme.colors.primary,
        elevation = 16.dp
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) { ->

                Text(text = item.id.toString())
                Text(text = item.listId.toString())
                item.name?.let { Text(text = it) }
            }
    }
}