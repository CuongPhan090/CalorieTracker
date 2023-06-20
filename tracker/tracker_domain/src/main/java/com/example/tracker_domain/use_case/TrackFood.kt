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
        food: TrackableFood,
        amount: Int,
        mealType: MealType,
        date: LocalDate
    ) {
        repository.insertTrackedFood(
            TrackedFood(
                name = food.name,
                imageUrl = food.imageUrl,
                carbs = ((food.carbPer100g / 100) * amount),
                protein = (food.proteinPer100g / 100) * amount,
                fat = (food.fatPer100g / 100) * amount,
                mealType = mealType,
                amount = amount,
                date = date,
                calories = (food.caloriesPer100g / 100) * amount
            )
        )
    }
}
