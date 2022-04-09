package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.repository.ItemRepository
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val itemRepository: ItemRepository) : ViewModel() {

    //You can use Flow at repository/data source level and convert it to livedata in ViewModel using Flow.asLiveData()

    // use viewModelScope to ensure this flow is lifecycle-aware
    private val _items: Flow<List<Item>> = itemRepository.getAllItems()
    val items = _items

    // this will be exposed for the recyclerview (lazy column)
    val sortedItemList: MutableState<List<Item>> = mutableStateOf(mutableListOf())

    init {

        // This will populate items from url with
        // objects converted from Json to an Item.
        // Will even observe changes to the json at that url!
        //retrofitController.initializeItems()

        // itemList should be populated; now sort
        //sortList(itemListFlow = items)
    }

    // TODO: here or fully SQL?
    private fun sortList(itemListFlow: StateFlow<List<Item>>) = viewModelScope.launch { ->

        itemListFlow.collect { itemList ->
            // sort as stated in instructions

            sortedItemList.value = itemList
        }

    }

}