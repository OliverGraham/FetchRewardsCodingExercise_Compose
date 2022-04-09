package com.projects.oliver_graham.fetchrewardscodingexercise_compose.data


import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.dependencyinjection.ApplicationScope
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.webservices.RetrofitController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

// Room will generate code for this class
@Database(entities = [Item::class], version = 1)
abstract class ItemDatabase: RoomDatabase() {

    abstract fun itemDao(): ItemDao

    class Callback@Inject constructor(
        private val database: Provider<ItemDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope,
        private val retrofitController: RetrofitController
    ) : RoomDatabase.Callback() {

        // execute this method only the first time this database is created
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val theDao = database.get().itemDao()
            applicationScope.launch {
                val items = retrofitController.getItems()
                items.collect { anItemList ->
                    for (item: Item in anItemList) {
                        theDao.insert(item)
                    }
                }
            }

        }

    }

}