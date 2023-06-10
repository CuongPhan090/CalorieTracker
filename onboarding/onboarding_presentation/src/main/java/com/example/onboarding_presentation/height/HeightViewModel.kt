package com.example.onboarding_presentation.height

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.core.data.preferences.DefaultPreferences
import com.example.core.domain.usecase.FilterOutDigit
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
class HeightViewModel @Inject constructor(
    private val defaultPreferences: DefaultPreferences,
    private val filterOutDigit: FilterOutDigit
) : ViewModel() {

    var height by mutableStateOf("0")
        private set

    private val _uiEvent = Channel<UiEvent>()
    val uiEvent = _uiEvent.receiveAsFlow()

    fun onHeightEnter(height: String) {
        if (height.length in 0..3) {
            this.height = filterOutDigit(height)
        }
    }

    fun onNextClick() {
        viewModelScope.launch {
            val heightNumber = height.toIntOrNull() ?: run {
                _uiEvent.send(
                    UiEvent.ShowSnackBar(
                        UiText.StringResource(R.string.error_height_cant_be_empty)
                    )
                )
                return@launch
            }
            defaultPreferences.saveHeight(heightNumber)
            _uiEvent.send(
                UiEvent.Navigate(Route.WEIGHT)
            )
        }
    }
}