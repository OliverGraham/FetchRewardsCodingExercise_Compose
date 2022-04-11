package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *  Contains data from retrofit's http request.
 *  Room will generate code for this class.
 */
@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    /**
    *  Adapted from https://gist.github.com/florina-muntenescu/697e543652b03d3d2a06703f5d6b44b5,
    *  (A member of the Android Development team at Google).
    *  Insert data on creation from retrofit call using separate thread
    * */
    companion object {

        @Volatile
        private var dbInstance: ItemDatabase? = null
        private val lock = Any()

        /**
        *   Overload constructor for ItemDatabase
        *   Return instance of database, or instantiate if needed
        */
        operator fun invoke(context: Context) = dbInstance ?: synchronized(lock) {
            dbInstance ?: createDatabase(context.applicationContext).also { dbInstance = it }
        }

        private fun createDatabase(context: Context) =
            Room.databaseBuilder(context, ItemDatabase::class.java, "item_database")
                .build()
    }
}