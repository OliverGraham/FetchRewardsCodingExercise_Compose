package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.CenteredContentRow
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.ExpandableCard

/**
 *  Display a list of cards, with content derived from Room,
 *  grouped together as specified.
 *  The cards can expand or contract to reveal the list content
 *  when clicked (or pressed)
 */
@OptIn(ExperimentalFoundationApi::class, ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(viewModel: HomeScreenViewModel) {

    val listState = rememberLazyListState()

    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxHeight()
    ) { ->
        items(viewModel.groupedItems.value.toList()) { (listId, itemList) ->
            CenteredContentRow { ->
                ExpandableCard(listId, itemList)
            }
            Spacer(modifier = Modifier.padding(vertical = 8.dp))
        }
    }
}