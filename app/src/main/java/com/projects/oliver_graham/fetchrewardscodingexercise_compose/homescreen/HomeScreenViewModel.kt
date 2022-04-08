package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val retrofitController: RetrofitController) : ViewModel() {

    private val items: StateFlow<List<Item>>
        get() = retrofitController.getItems()

    // this will be exposed for the recyclerview (lazy column)
    val sortedItemList: MutableState<List<Item>> = mutableStateOf(mutableListOf())

    init {

        // This will populate items from url with
        // objects converted from Json to an Item.
        // Will even observe changes to the json at that url!
        retrofitController.initializeItems()

        // itemList should be populated; now sort
        sortList(itemListFlow = items)
    }

    private fun sortList(itemListFlow: StateFlow<List<Item>>) = viewModelScope.launch { ->

        itemListFlow.collect { itemList ->
            // sort as stated in instructions

            sortedItemList.value = itemList
        }

    }

}