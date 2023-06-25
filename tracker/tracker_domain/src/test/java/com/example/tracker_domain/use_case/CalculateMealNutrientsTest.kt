package com.example.tracker_domain.use_case

import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.tracker_domain.model.MealType
import com.example.tracker_domain.model.TrackedFood
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Test
import java.time.LocalDate
import kotlin.random.Random

class CalculateMealNutrientsTest {

    private lateinit var calculateMealNutrients: CalculateMealNutrients
    private val sharedPref = mockk<Preferences>(relaxed = true) {
        every { loadUserInfo() } returns getUserInfo()
    }


    @Before
    fun setup() {
        calculateMealNutrients = CalculateMealNutrients(sharedPref)
    }

    @Test
    fun `Carbs, protein, fat, calories are properly calculated for breakfast`() {
        with(getTrackedFoods()) {
            val result = calculateMealNutrients(this)
            val calculatedCarbs = result.mealNutrients.values
                .filter { it.mealType is MealType.Breakfast }
                .sumOf { it.carbs }
            val expectedCarbs = this.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.carbs }

            assertThat(calculatedCarbs).isEqualTo(expectedCarbs)

            val calculatedProtein = result.mealNutrients.values
                .filter { it.mealType is MealType.Breakfast }
                .sumOf { it.protein }
            val expectedProtein = this.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.protein }

            assertThat(calculatedProtein).isEqualTo(expectedProtein)

            val calculatedFat = result.mealNutrients.values
                .filter { it.mealType is MealType.Breakfast }
                .sumOf { it.fat }
            val expectedFat = this.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.fat }

            assertThat(calculatedFat).isEqualTo(expectedFat)

            val calculatedCalories = result.mealNutrients.values
                .filter { it.mealType is MealType.Breakfast }
                .sumOf { it.calories }
            val expectedCalories = this.filter { it.mealType is MealType.Breakfast }
                .sumOf { it.calories }

            assertThat(calculatedCalories).isEqualTo(expectedCalories)
        }
    }

    @Test
    fun `Carbs, protein, fat, calories are properly calculated for lunch`() {
        with(getTrackedFoods()) {
            val result = calculateMealNutrients(this)
            val calculatedCarbs = result.mealNutrients.values
                .filter { it.mealType is MealType.Lunch }
                .sumOf { it.carbs }
            val expectedCarbs = this.filter { it.mealType is MealType.Lunch }
                .sumOf { it.carbs }

            assertThat(calculatedCarbs).isEqualTo(expectedCarbs)

            val calculatedProtein = result.mealNutrients.values
                .filter { it.mealType is MealType.Lunch }
                .sumOf { it.protein }
            val expectedProtein = this.filter { it.mealType is MealType.Lunch }
                .sumOf { it.protein }

            assertThat(calculatedProtein).isEqualTo(expectedProtein)

            val calculatedFat = result.mealNutrients.values
                .filter { it.mealType is MealType.Lunch }
                .sumOf { it.fat }
            val expectedFat = this.filter { it.mealType is MealType.Lunch }
                .sumOf { it.fat }

            assertThat(calculatedFat).isEqualTo(expectedFat)

            val calculatedCalories = result.mealNutrients.values
                .filter { it.mealType is MealType.Lunch }
                .sumOf { it.calories }
            val expectedCalories = this.filter { it.mealType is MealType.Lunch }
                .sumOf { it.calories }

            assertThat(calculatedCalories).isEqualTo(expectedCalories)
        }
    }

    @Test
    fun `Carbs, protein, fat, calories are properly calculated for dinner`() {
        with(getTrackedFoods()) {
            val result = calculateMealNutrients(this)
            val calculatedCarbs = result.mealNutrients.values
                .filter { it.mealType is MealType.Dinner }
                .sumOf { it.carbs }
            val expectedCarbs = this.filter { it.mealType is MealType.Dinner }
                .sumOf { it.carbs }

            assertThat(calculatedCarbs).isEqualTo(expectedCarbs)

            val calculatedProtein = result.mealNutrients.values
                .filter { it.mealType is MealType.Dinner }
                .sumOf { it.protein }
            val expectedProtein = this.filter { it.mealType is MealType.Dinner }
                .sumOf { it.protein }

            assertThat(calculatedProtein).isEqualTo(expectedProtein)

            val calculatedFat = result.mealNutrients.values
                .filter { it.mealType is MealType.Dinner }
                .sumOf { it.fat }
            val expectedFat = this.filter { it.mealType is MealType.Dinner }
                .sumOf { it.fat }

            assertThat(calculatedFat).isEqualTo(expectedFat)

            val calculatedCalories = result.mealNutrients.values
                .filter { it.mealType is MealType.Dinner }
                .sumOf { it.calories }
            val expectedCalories = this.filter { it.mealType is MealType.Dinner }
                .sumOf { it.calories }

            assertThat(calculatedCalories).isEqualTo(expectedCalories)
        }
    }

    @Test
    fun `Carbs, protein, fat, calories are properly calculated for snack`() {
        with(getTrackedFoods()) {
            val result = calculateMealNutrients(this)
            val calculatedCarbs = result.mealNutrients.values
                .filter { it.mealType is MealType.Snack }
                .sumOf { it.carbs }
            val expectedCarbs = this.filter { it.mealType is MealType.Snack }
                .sumOf { it.carbs }

            assertThat(calculatedCarbs).isEqualTo(expectedCarbs)

            val calculatedProtein = result.mealNutrients.values
                .filter { it.mealType is MealType.Snack }
                .sumOf { it.protein }
            val expectedProtein = this.filter { it.mealType is MealType.Snack }
                .sumOf { it.protein }

            assertThat(calculatedProtein).isEqualTo(expectedProtein)

            val calculatedFat = result.mealNutrients.values
                .filter { it.mealType is MealType.Snack }
                .sumOf { it.fat }
            val expectedFat = this.filter { it.mealType is MealType.Snack }
                .sumOf { it.fat }

            assertThat(calculatedFat).isEqualTo(expectedFat)

            val calculatedCalories = result.mealNutrients.values
                .filter { it.mealType is MealType.Snack }
                .sumOf { it.calories }
            val expectedCalories = this.filter { it.mealType is MealType.Snack }
                .sumOf { it.calories }

            assertThat(calculatedCalories).isEqualTo(expectedCalories)
        }
    }

    private fun getTrackedFoods(): List<TrackedFood> = (1..30).map {
        TrackedFood(
            name = "name",
            imageUrl = null,
            carbs = Random.nextInt(100),
            protein = Random.nextInt(100),
            fat = Random.nextInt(100),
            mealType = listOf(
                MealType.fromString("breakfast"),
                MealType.fromString("lunch"),
                MealType.fromString("dinner"),
                MealType.fromString("snack")
            ).random(),
            amount = Random.nextInt(200),
            date = LocalDate.now(),
            calories = Random.nextInt(2000)
        )
    }


    private fun getUserInfo() = UserInfo(
        gender = Gender.Male,
        age = 25,
        weight = 150f,
        height = 170,
        activityLevel = ActivityLevel.Medium,
        goalType = GoalType.KeepWeight,
        carbRatio = .3f,
        proteinRatio = .4f,
        fatRatio = .3f
    )
}