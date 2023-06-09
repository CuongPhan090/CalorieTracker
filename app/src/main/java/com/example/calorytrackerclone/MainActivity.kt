package com.example.calorytrackerclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calorytrackerclone.navigation.navigate
import com.example.calorytrackerclone.ui.theme.CaloryTrackerCloneTheme
import com.example.core.navigation.Route
import com.example.onboarding_presentation.age.AgeScreen
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.height.HeightScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerCloneTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    scaffoldState = scaffoldState
                ) {
                    NavHost(navController = navController, startDestination = Route.WELCOME) {
                        composable(route = Route.WELCOME) {
                            WelcomeScreen(onNavigate = navController::navigate)
                        }

                        composable(route = Route.AGE) {
                            AgeScreen(
                                onNavigate = navController::navigate,
                                scaffoldState = scaffoldState
                            )
                        }

                        composable(route = Route.GENDER) {
                            GenderScreen(onNavigate = navController::navigate)
                        }
                        composable(route = Route.HEIGHT) {
                            HeightScreen(
                                onNavigate = navController::navigate,
                                scaffoldState = scaffoldState
                            )
                        }
                        composable(route = Route.WEIGHT) {

                        }
                        composable(route = Route.NUTRITION_GOAL) {

                        }
                        composable(route = Route.ACTIVITY) {

                        }
                        composable(route = Route.GOAL) {

                        }
                        composable(route = Route.TRACKER_OVERVIEW) {

                        }
                        composable(route = Route.SEARCH) {

                        }
                    }
                }
            }
        }
    }
}

