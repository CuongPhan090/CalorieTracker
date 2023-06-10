package com.example.onboarding_presentation.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.preferences.DefaultPreferences
import com.example.core.domain.model.ActivityLevel
import com.example.core.navigation.Route
import com.example.core.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val defaultPreferences: DefaultPreferences
) : ViewModel() {

    var activityLevel by mutableStateOf<ActivityLevel>(ActivityLevel.Medium)
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onActivityLevelSelect(activityLevel: ActivityLevel) {
        this.activityLevel = activityLevel
    }

    fun onNextClick() {
        viewModelScope.launch {
            _uiEvent.send(
                UiEvent.Navigate(
                    Route.GOAL
                )
            )
            defaultPreferences.saveActivityLevel(activityLevel)
        }
    }
}
