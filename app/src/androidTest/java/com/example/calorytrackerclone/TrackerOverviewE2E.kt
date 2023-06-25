package com.example.calorytrackerclone

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calorytrackerclone.navigation.Route
import com.example.calorytrackerclone.repository.TrackerRepositoryFake
import com.example.calorytrackerclone.ui.theme.CaloryTrackerCloneTheme
import com.example.core.domain.model.ActivityLevel
import com.example.core.domain.model.Gender
import com.example.core.domain.model.GoalType
import com.example.core.domain.model.UserInfo
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.usecase.FilterOutDigit
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.use_case.*
import com.example.tracker_presentation.search.SearchScreen
import com.example.tracker_presentation.search.SearchViewModel
import com.example.tracker_presentation.tracker_overview.TrackerOverviewScreen
import com.example.tracker_presentation.tracker_overview.TrackerOverviewViewModel
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.every
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.math.roundToInt

@HiltAndroidTest
class TrackerOverviewE2E {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    private lateinit var repositoryFake: TrackerRepositoryFake
    private lateinit var trackerUseCase: TrackerUseCase
    private lateinit var trackerOverViewModel: TrackerOverviewViewModel
    private lateinit var searchViewModel: SearchViewModel
    private lateinit var preferences: Preferences

    private lateinit var navController: NavHostController

    @Before
    fun setup() {
        repositoryFake = TrackerRepositoryFake()
        preferences = mockk(relaxed = true) {
            every { loadUserInfo() } returns getUserInfo()
        }
        trackerUseCase = TrackerUseCase(
            trackFood = TrackFood(repositoryFake),
            searchFood = SearchFood(repositoryFake),
            getFoodForDate = GetFoodForDate(repositoryFake),
            deleteTrackedFood = DeleteTrackedFood(repositoryFake),
            calculateMealNutrients = CalculateMealNutrients(preferences)
        )
        trackerOverViewModel = TrackerOverviewViewModel(preferences, trackerUseCase)
        searchViewModel = SearchViewModel(trackerUseCase, FilterOutDigit())

        composeRule.setContent {
            CaloryTrackerCloneTheme {
                navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = Route.TRACKER_OVERVIEW
                    ) {
                        composable(route = Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealName, day, month, year ->
                                    navController.navigate(
                                        route = Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                                    )
                                },
                                viewModel = trackerOverViewModel
                            )
                        }
                        composable(route = Route.SEARCH + "/{mealName}/{dayOfMonth}/{month}/{year}",
                            arguments = listOf(
                                navArgument("mealName") {
                                    type = NavType.StringType
                                },
                                navArgument("dayOfMonth") {
                                    type = NavType.IntType
                                },
                                navArgument("month") {
                                    type = NavType.IntType
                                },
                                navArgument("year") {
                                    type = NavType.IntType
                                }
                            )
                        ) {
                            val meal = it.arguments?.getString("mealName") ?: ""
                            val dayOfMonth = it.arguments?.getInt("dayOfMonth") ?: 0
                            val month = it.arguments?.getInt("month") ?: 0
                            val year = it.arguments?.getInt("year") ?: 0
                            SearchScreen(
                                scaffoldState = scaffoldState,
                                mealName = meal,
                                dayOfMonth = dayOfMonth,
                                month = month,
                                year = year,
                                onNavigateUp = {
                                    navController.navigateUp()
                                },
                                viewModel = searchViewModel
                            )
                        }
                    }
                }
            }
        }
    }

    @Test
    fun addBreakfast_appearsUnderBreakfast_nutrientProperlyCalculated() {
        repositoryFake.searchResult = listOf(
            TrackableFood(
                name = "chicken",
                imageUrl = null,
                caloriesPer100g = 150,
                proteinPer100g = 5,
                carbPer100g = 50,
                fatPer100g = 1
            )
        )

        val addAmount = 150
        val expectedCalories = (1.5f * 150).roundToInt()
        val expectedCarbs = (1.5f * 50).roundToInt()
        val expectedProtein = (1.5f * 5).roundToInt()
        val expectedFat = (1.5f * 1).roundToInt()

        with(composeRule) {
            onNodeWithText(text = "Add Breakfast")
                .assertDoesNotExist()

            onNodeWithContentDescription(label = "Breakfast")
                .performClick()

            onNodeWithText(text = "Add Breakfast")
                .assertIsDisplayed()
                .performClick()

            assertThat(navController.currentDestination?.route?.startsWith(Route.SEARCH)).isTrue()

            onNodeWithContentDescription(label = "Search text field")
                .performTextInput("chicken")

            onNodeWithContentDescription(label = "Search...")
                .performClick()

            onNodeWithText(text = "Carbs")
                .performClick()

            onNodeWithContentDescription(label = "Amount")
                .performTextInput(text = addAmount.toString())

            onNodeWithContentDescription(label = "Track")
                .performClick()

            assertThat(navController.currentDestination?.route?.startsWith(Route.TRACKER_OVERVIEW)).isTrue()

            onAllNodesWithText(text = expectedCalories.toString())
                .onFirst()
                .assertIsDisplayed()

            onAllNodesWithText(text = expectedCarbs.toString())
                .onFirst()
                .assertIsDisplayed()

            onAllNodesWithText(text = expectedProtein.toString())
                .onFirst()
                .assertIsDisplayed()

            onAllNodesWithText(text = expectedFat.toString())
                .onFirst()
                .assertIsDisplayed()
        }
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