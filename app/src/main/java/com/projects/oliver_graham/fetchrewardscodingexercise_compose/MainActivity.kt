// Author:      Oliver Graham
// Email:       olivergraham916@gmail.com
// Date:        April 10th, 2022
// Description: Fetch Rewards Coding Exercise.
//              UI built using Jetpack Compose.
//              Thanks for checking out my work!

package com.projects.oliver_graham.fetchrewardscodingexercise_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.ItemDatabase
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen.HomeScreen
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen.HomeScreenViewModel
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.repository.ItemRepository
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.theme.FetchRewardsCodingExercise_ComposeTheme
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create dependencies, will be injected shortly
        val dao = ItemDatabase(this).itemDao()
        val scope = CoroutineScope(Dispatchers.IO)

        // use context that lives as long as this activity
        RetrofitController.initialize(dao, scope, this)
        
        setContent {
            FetchRewardsCodingExercise_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(
                        viewModel = HomeScreenViewModel(
                            itemRepository = ItemRepository(dao)
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

    RetrofitController.initialize(dao, scope, LocalContext.current)

    FetchRewardsCodingExercise_ComposeTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            HomeScreen(
                viewModel = HomeScreenViewModel(
                    itemRepository = ItemRepository(dao)
                )
            )
        }
    }
}