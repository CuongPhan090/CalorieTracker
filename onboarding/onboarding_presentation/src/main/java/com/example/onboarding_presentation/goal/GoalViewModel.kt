package com.example.onboarding_presentation.goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.preferences.DefaultPreferences
import com.example.core.domain.model.GoalType
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val defaultPreferences: DefaultPreferences
): ViewModel() {

    var goal by mutableStateOf<GoalType>(GoalType.KeepWeight)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onGoalSelect(goalType: GoalType) {
        this.goal = goalType
    }

    fun onNextClick() {
        viewModelScope.launch {
            _uiEvent.send(UiEvent.Navigate(Route.NUTRITION_GOAL))
            defaultPreferences.saveGoalType(goal)
        }
    }
}
