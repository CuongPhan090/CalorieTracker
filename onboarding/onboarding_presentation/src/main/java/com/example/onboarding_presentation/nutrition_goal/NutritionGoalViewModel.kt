package com.example.onboarding_presentation.nutrition_goal

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.domain.preferences.Preferences
import com.example.core.domain.usecase.FilterOutDigit
import com.example.core.util.UiEvent
import com.example.onboarding_domain.use_case.ValidateNutritions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NutritionGoalViewModel @Inject constructor(
    private val preferences: Preferences,
    private val filterOutDigit: FilterOutDigit,
    private val validateNutritions: ValidateNutritions
) : ViewModel() {

    var state by mutableStateOf(NutritionGoalState())
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onEvent(event: NutritionGoalEvent) {
        when (event) {
            is NutritionGoalEvent.OnCarbRatioEnter -> {
                state = state.copy(
                    carbRatio = filterOutDigit(event.ratio)
                )
            }
            is NutritionGoalEvent.OnProteinRatioEnter -> {
                state = state.copy(
                    proteinRatio = filterOutDigit(event.ratio)
                )
            }
            is NutritionGoalEvent.OnFatRatioEnter -> {
                state = state.copy(
                    fatRatio = filterOutDigit(event.ratio)
                )
            }
            is NutritionGoalEvent.OnNextClick -> {
                val result = validateNutritions(
                    carbRatioText = state.carbRatio,
                    proteinRatioText = state.proteinRatio,
                    fatRatioText = state.fatRatio
                )
                when (result) {
                    is ValidateNutritions.Result.Success -> {
                        preferences.saveCarbRatio(result.carbRatio)
                        preferences.saveProteinRatio(result.proteinRatio)
                        preferences.saveFatRatio(result.fatRatio)

                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.Success)
                        }
                    }

                    is ValidateNutritions.Result.Error -> {
                        viewModelScope.launch {
                            _uiEvent.send(UiEvent.ShowSnackBar(message = result.message))
                        }
                    }
                }
            }
        }
    }
}