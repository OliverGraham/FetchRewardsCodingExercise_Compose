package com.projects.oliver_graham.fetchrewardscodingexercise_compose.dependencyinjection

import android.app.Application
import androidx.room.Room
import com.projects.oliver_graham.fetchrewardscodingexercise_compose.data.ItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton


// Use Hilt to generate the code necessary for dependency injection;
// this gives compile-time safety and performance benefits, opposed
// to a library like Koin that generates at runtime

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    // Hilt gets the application context and injects here when building
    // the database instance. Very nice!
    @Provides
    @Singleton
    fun provideDatabase(
        app: Application,
        callback: ItemDatabase.Callback
    ) = Room.databaseBuilder(app, ItemDatabase::class.java, "item_database")
        .fallbackToDestructiveMigration()
        .addCallback(callback)
        .build()

    @Provides
    fun provideItemDao(database: ItemDatabase) = database.itemDao()

    @ApplicationScope
    @Provides
    @Singleton
    fun providesApplicationScope() = CoroutineScope(SupervisorJob())
}

// this is how we crete our own annotated class
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope