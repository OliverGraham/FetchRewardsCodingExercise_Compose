// Author:      Oliver Graham
// Date:        April 8th, 2022
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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        // create dependencies, will be injected where needed
        val dao = ItemDatabase(this).itemDao()
        val scope = CoroutineScope(Dispatchers.IO)

        RetrofitController.initialize(dao, scope)
        val itemRepository = ItemRepository(dao, scope)
        
        setContent {
            FetchRewardsCodingExercise_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(HomeScreenViewModel(itemRepository))
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    FetchRewardsCodingExercise_ComposeTheme {
        Greeting("Android")
    }
}