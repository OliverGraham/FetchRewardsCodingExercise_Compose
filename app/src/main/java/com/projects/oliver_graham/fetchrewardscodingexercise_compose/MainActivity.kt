// Author:      Oliver Graham
// Date:        April 8th, 2022
// Description: Fetch Rewards Coding Exercise
//              This app is meant to display my skills
//              and the types of libraries and design patterns I'm familiar with.
//              The UI for this version is built using Jetpack Compose

package com.projects.oliver_graham.fetchrewardscodingexercise_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen.HomeScreen
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen.HomeScreenViewModel
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.ui.theme.FetchRewardsCodingExercise_ComposeTheme
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val homeScreenViewModel: HomeScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // create dependencies
        val retrofitController = RetrofitController()

        CoroutineScope(Dispatchers.IO).launch {
            retrofitController.initializeItems()
        }

        setContent {
            FetchRewardsCodingExercise_ComposeTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    HomeScreen(homeScreenViewModel)
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