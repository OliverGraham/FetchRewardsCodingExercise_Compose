package com.projects.oliver_graham.fetchrewardscodingexercise_compose.repository

import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.ItemDao
import kotlinx.coroutines.CoroutineScope

//Repository classes are responsible for the following tasks:
//
//Exposing data to the rest of the app.
//Centralizing changes to the data.
//Resolving conflicts between multiple data sources.
//Abstracting sources of data from the rest of the app.
//Containing business logic.

class ItemRepository(
    private val dao: ItemDao,
    scope: CoroutineScope
    ) {

    // forceUpdate()
    // createDatabase()
    // getItemsFromApi()

    // ~~ filter stuff, only talk to db to get info room ~~

    // fun getAllItems() = dao.getAllItems()
    fun getAllItems() = dao.getNonNullItems()
    // getGroupedAndFilteredItems()
}