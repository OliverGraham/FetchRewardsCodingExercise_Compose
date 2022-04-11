package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 *  Tests for Room.
 *  Checks that Item objects are inserted correctly
 *  and that the filter query works as intended.
 * */
@RunWith(AndroidJUnit4::class)
class ItemDatabaseTest : TestCase() {

    private lateinit var database: ItemDatabase
    private lateinit var itemDao: ItemDao

    @Before
    public override fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, ItemDatabase::class.java).build()
        itemDao = database.itemDao()
    }

    @Test
    fun insertItemList() = runBlocking { ->

        // create test list and insert into database
        val itemList = listOf(Item(1, 1, "Item 1"), Item(5, 1, "Item 5"))
        itemDao.insertAllItems(itemList)

        // collect the first item from the flow and stop
        val itemsFromDatabase: List<Item> = itemDao.getAllItems().first()

        // should be two items in the list
        assertEquals(2, itemsFromDatabase.size)
    }

    @Test
    fun insertItemList_WithNulls() = runBlocking { ->

        val testItemName = "Item 35"

        // create test list with null values and insert into database
        val itemList = listOf(Item(1, 1, ""), Item(35, 4, testItemName), Item(5, 1, null))
        itemDao.insertAllItems(itemList)

        // collect the first item from the flow and stop
        val itemsFromDatabase: List<Item> = itemDao.getNonNullItems().first()

        // only item 35 should be in the list, if the above query was successful
        assertEquals(testItemName, itemsFromDatabase[0].name)
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    public override fun tearDown() {}
}