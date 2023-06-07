package com.example.calorytrackerclone.di

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.core.data.preferences.DefaultPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(app: Application): SharedPreferences {
        return app.getSharedPreferences("SharedPreferences", MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun provideDefaultPreferences(sharedPreferences: SharedPreferences): DefaultPreferences {
        return DefaultPreferences(sharedPreferences)
    }
}