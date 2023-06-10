package com.example.onboarding_presentation.nutrition_goal

/**
 * Specify all the events user can interact with
 */
sealed class NutritionGoalEvent {
    data class OnCarbRatioEnter(val ratio: String): NutritionGoalEvent()
    data class OnProteinRatioEnter(val ratio: String): NutritionGoalEvent()
    data class OnFatRatioEnter(val ratio: String): NutritionGoalEvent()
    object OnNextClick : NutritionGoalEvent()
}
