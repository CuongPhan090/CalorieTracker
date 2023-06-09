package com.example.core.util

/**
 * This class defined all kind of events that we would like to send from viewModel to composable
 * such as navigating to different screen, popping the backstack, showing a snack bar.
 * Anything we want to do with the UI just once, not a state
 */
sealed class UiEvent {
    data class Navigate(val route: String) : UiEvent()
    object NavigateUp : UiEvent()
    data class ShowSnackBar(val message: UiText): UiEvent()
}
