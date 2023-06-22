package com.example.tracker_presentation.tracker_overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import com.example.core.R
import com.example.tracker_presentation.tracker_overview.component.*

@Composable
fun TrackerOverviewScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: TrackerOverviewViewModel = hiltViewModel()
) {
    val spacing = LocalSpacing.current
    val state = viewModel.state
    val context = LocalContext.current

    LaunchedEffect(key1 = context) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                else -> Unit
            }
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = spacing.spaceMedium)
    ) {
        item {
            NutrientsHeader(state = state)
            Spacer(Modifier.height(spacing.spaceMedium))
            DaySelector(
                date = state.date,
                onPreviousDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnPreviousDayClick)
                },
                onNextDayClick = {
                    viewModel.onEvent(TrackerOverviewEvent.OnNextDayClick)
                }
            )
            Spacer(Modifier.height(spacing.spaceMedium))
        }

        items(state.meals) { meal ->
            ExpandableMeal(
                meal = meal,
                onToggleClick = {
                    viewModel.onEvent(
                        TrackerOverviewEvent.OnToggleMealClick(meal)
                    )
                },
                content = {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        state.trackedFoods.forEach { food ->
                            TrackedFoodItem(trackedFood = food) {
                                viewModel.onEvent(
                                    TrackerOverviewEvent.OnDeleteTrackedFoodClick(food)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(spacing.spaceMedium))

                    AddButton(
                        text = stringResource(id = R.string.add_meal, meal.name.asString(context)),
                        onClick = {
                            viewModel.onEvent(TrackerOverviewEvent.OnAddFoodClick(meal))
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}