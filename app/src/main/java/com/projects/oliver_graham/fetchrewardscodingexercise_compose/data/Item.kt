package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/* List item, populated by JSON object */
@Entity(tableName = "item_table")
@Parcelize
data class Item(
    @PrimaryKey val id: Int,        // this value is unique in the given JSON
    val listId: Int,
    val name: String?
) : Parcelable
