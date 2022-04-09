package com.projects.oliver_graham.fetchrewardscodingexercise_compose

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// sets up Hilt, also need to add android:name=".FetchApplication" in manifest
@HiltAndroidApp
class FetchApplication : Application() {}
