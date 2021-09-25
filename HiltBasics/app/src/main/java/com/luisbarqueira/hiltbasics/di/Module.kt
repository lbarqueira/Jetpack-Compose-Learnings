package com.luisbarqueira.hiltbasics.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

//! In Kotlin, modules that only contain @Provides functions can be object classes.

@Module
@InstallIn(ActivityComponent::class)

object ServiceModule {

    @Provides
    fun provideAnalyticsServiceString(
        //! Potential dependencies of this type
    ): String {
        return "Dependency provided in Hilt Module"
    }

}