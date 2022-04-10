// Author:      Oliver Graham
// Date:        April 10th, 2022
// Description: Fetch Rewards Coding Exercise.
//              UI built using Jetpack Compose.
//              Basically, I want to show what kinds of libraries I'm
//              familiar with and that I can get them working. It's
//              overkill for such a simple app, but I hope my reasoning
//              make sense.

package com.projects.oliver_graham.fetchrewardscodingexercise_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.ItemDatabase
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen.HomeScreen
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen.HomeScreenViewModel
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.repository.ItemRepository
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.CenteredContentRow
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.ExpandableCard
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.theme.FetchRewardsCodingExercise_ComposeTheme
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create dependencies, will be injected where needed
        val dao = ItemDatabase(this).itemDao()
        val scope = CoroutineScope(Dispatchers.IO)

        RetrofitController.initialize(dao, scope)
        
        setContent {
            FetchRewardsCodingExercise_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(
                        viewModel = HomeScreenViewModel(
                            itemRepository = ItemRepository(dao, scope)
                        )
                    )
                }
            }
        }
    }
}

/**
 *  Preview for HomeScreen.
 *  Construct all dependencies here;
 *  @PreviewParameter seems unable to work with a ViewModel.
 */
@Preview(showBackground = true)
@OptIn(ExperimentalAnimationApi::class)
@Composable
private fun HomeScreenPreview() {

    // create dependencies, will be injected where needed
    val dao = ItemDatabase(LocalContext.current).itemDao()
    val scope = CoroutineScope(Dispatchers.IO)

    RetrofitController.initialize(dao, scope)

    FetchRewardsCodingExercise_ComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                viewModel = HomeScreenViewModel(
                    itemRepository = ItemRepository(dao, scope)
                )
            )
        }
    }
}