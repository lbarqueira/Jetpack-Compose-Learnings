package com.luisbarqueira.hiltbasics

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

// First, enable Hilt in your app by annotating your application class with the @HiltAndroidApp
// to trigger Hilt’s code generation
@HiltAndroidApp
class MyApplication : Application()