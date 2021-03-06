package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 *  Contains the two necessary operations, and
 *  has two more for fun. Of special note is the filtering
 *  query, getNonNullItems().
 */
@Dao
interface ItemDao {

    @Query("SELECT * FROM item_table")
    fun getAllItems(): Flow<List<Item>>

    /* Filter out any null or empty string */
    @Query("SELECT * FROM item_table WHERE name IS NOT NULL AND name IS NOT ''")
    fun getNonNullItems(): Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: Item)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllItems(items: List<Item>)
}