package com.example.onboarding_presentation.weight

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.preferences.DefaultPreferences
import com.example.core.util.UiEvent
import com.example.core.util.UiText
import com.example.core.R
import com.example.core.navigation.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeightViewModel @Inject constructor(
    private val defaultPreferences: DefaultPreferences
) : ViewModel() {
    var weight by mutableStateOf("0")
        private set

    private var _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onWeightEnter(weight: String) {
        if (weight.length in 0..5) {
            this.weight = weight
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val weightNumber = weight.toFloatOrNull() ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(
                            R.string.error_weight_cant_be_empty
                        )
                    )
                )
                return@launch
            }
            defaultPreferences.saveWeight(weightNumber)
            _uiEvent.send(
                UiEvent.Navigate(
                    Route.ACTIVITY
                )
            )
        }
    }
}
