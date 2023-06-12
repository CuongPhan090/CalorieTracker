package com.example.tracker_domain.use_case


data class TrackerUseCase (
    val trackFood: TrackFood,
    val searchFood: SearchFood,
    val getFoodForDate: GetFoodForDate,
    val deleteTrackedFood: DeleteTrackedFood,
    val calculateMealNutrients: CalculateMealNutrients
)
