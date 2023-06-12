package com.example.tracker_domain.di

import com.example.core.domain.preferences.Preferences
import com.example.tracker_domain.TrackerRepository
import com.example.tracker_domain.use_case.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object TrackerDomainModule {

    @Provides
    @ViewModelScoped
    fun provideTrackerUseCase(
        repository: TrackerRepository,
        preferences: Preferences
    ): TrackerUseCase {
        return TrackerUseCase(
            trackFood = TrackFood(repository),
            searchFood = SearchFood(repository),
            getFoodForDate = GetFoodForDate(repository),
            deleteTrackedFood = DeleteTrackedFood(repository),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
    }
}
