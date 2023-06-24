package com.example.calorytrackerclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.calorytrackerclone.ui.theme.CaloryTrackerCloneTheme
import com.example.core.domain.preferences.Preferences
import com.example.calorytrackerclone.navigation.Route
import com.example.onboarding_presentation.activity.ActivityScreen
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.goal.GoalScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.nutrition_goal.NutritionGoalScreen
import com.example.onboarding_presentation.weight.WeightScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import com.example.tracker_presentation.search.SearchScreen
import com.example.tracker_presentation.tracker_overview.TrackerOverviewScreen
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val shouldShowOnBoarding = preferences.loadShouldShowOnboarding()

        setContent {
            CaloryTrackerCloneTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = if (shouldShowOnBoarding) Route.WELCOME else Route.TRACKER_OVERVIEW
                    ) {
                        composable(route = Route.WELCOME) {
                            WelcomeScreen(
                                onNextClick = {
                                    navController.navigate(Route.GENDER)
                                })
                        }
                        composable(route = Route.AGE) {
                            AgeScreen(
                                onNextClick = {
                                    navController.navigate(Route.HEIGHT)
                                },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(route = Route.GENDER) {
                            GenderScreen(
                                onNextClick = {
                                    navController.navigate(Route.AGE)
                                })
                        }
                        composable(route = Route.HEIGHT) {
                            HeightScreen(
                                onNextClick = {
                                    navController.navigate(Route.WEIGHT)
                                },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(route = Route.WEIGHT) {
                            WeightScreen(
                                onNextClick = {
                                    navController.navigate(Route.ACTIVITY)
                                },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(route = Route.NUTRITION_GOAL) {
                            NutritionGoalScreen(
                                onNextClick = {
                                    navController.navigate(Route.TRACKER_OVERVIEW)
                                },
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(route = Route.ACTIVITY) {
                            ActivityScreen(
                                onNextClick = {
                                    navController.navigate(Route.GOAL)
                                },
                            )
                        }
                        composable(route = Route.GOAL) {
                            GoalScreen(
                                onNextClick = {
                                    navController.navigate(Route.NUTRITION_GOAL)
                                },
                            )
                        }
                        composable(route = Route.TRACKER_OVERVIEW) {
                            TrackerOverviewScreen(
                                onNavigateToSearch = { mealName, day, month, year ->
                                    navController.navigate(
                                        route = Route.SEARCH + "/$mealName" + "/$day" + "/$month" + "/$year"
                                    )
                                }
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
                                })
                        }
                    }
                }
            }
        }
    }
}

