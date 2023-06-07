package com.example.calorytrackerclone.navigation

import androidx.navigation.NavController
import com.example.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    navigate(route = event.route)
}
