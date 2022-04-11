package com.projects.oliver_graham.fetchrewardscodingexercise_compose.repository

import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.ItemDao

/**
 * Exposes access to the app's data
 * */
class ItemRepository(private val dao: ItemDao) {

    fun getAllItems() = dao.getAllItems()
    fun getNonNullItems() = dao.getNonNullItems()
}