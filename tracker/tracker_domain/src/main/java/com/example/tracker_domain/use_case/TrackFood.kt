package com.example.tracker_domain.use_case

import com.example.tracker_domain.TrackerRepository
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import java.time.LocalDate

class TrackFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        trackableFood: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = trackableFood.name,
                imageUrl = trackableFood.imageUrl,
                carbs = ((trackableFood.carbPer100g / 100) * amount),
                protein = (trackableFood.proteinPer100g / 100) * amount,
                fat = (trackableFood.fatPer100g / 100) * amount,
                mealType = mealType,
                amount = amount,
                date = date,
                calories = (trackableFood.caloriesPer100g / 100) * amount
            )
        )
    }
}
