package com.example.calorytrackerclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.calorytrackerclone.navigation.navigate
import com.example.calorytrackerclone.ui.theme.CaloryTrackerCloneTheme
import com.example.core.navigation.Route
import com.example.onboarding_presentation.gender.GenderScreen
import com.example.onboarding_presentation.welcome.WelcomeScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CaloryTrackerCloneTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Route.WELCOME) {
                    composable(route = Route.WELCOME) {
                        WelcomeScreen(onNavigate = navController::navigate)
                    }

                    composable(route = Route.AGE) {

                    }

                    composable(route = Route.GENDER) {
                        GenderScreen(onNavigate = navController::navigate)
                    }
                    composable(route = Route.HEIGHT) {

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

