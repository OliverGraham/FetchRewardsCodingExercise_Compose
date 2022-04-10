package com.projects.oliver_graham.fetchrewardscodingexercise_compose.homescreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.Item
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.repository.ItemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeScreenViewModel(itemRepository: ItemRepository) : ViewModel() {

    private val items: Flow<List<Item>> = itemRepository.getAllItems()

    // this will be exposed for the recyclerview (lazy column)
    val groupedItems: MutableState<Map<Int, List<Item>>> = mutableStateOf(mutableMapOf())

    init {
        viewModelScope.launch { ->
            items.collect { itemList ->

                // sort as stated in instructions
                // first by listId, then by name
                groupedItems.value = itemList.groupBy { it.listId }.toSortedMap()
            }
        }
    }
}