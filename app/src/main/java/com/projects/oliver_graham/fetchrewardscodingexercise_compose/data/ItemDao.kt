package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {

    /** Get all items from table
    *   Room will notify the flow anytime there's a change in the database
    */
    @Query("SELECT * FROM item_table")
    fun getAllItems(): Flow<List<Item>>

    // TODO: full filter query here
    @Query("SELECT * FROM item_table WHERE name IS NOT NULL")
    fun getNonNullItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items: List<Item>)
}