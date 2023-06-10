package com.example.onboarding_presentation.nutrition_goal

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.core.R
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.components.ActionButton
import com.example.components.UnitTextField
import com.example.core.util.UiEvent
import com.example.core_ui.LocalSpacing
import kotlinx.coroutines.flow.collect

@Composable
fun NutritionGoalScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    viewModel: NutritionGoalViewModel = hiltViewModel(),
    scaffoldState: ScaffoldState
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.ShowSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = event.message.asString(context)
                    )
                }
                else -> Unit
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(LocalSpacing.current.spaceLarge)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.what_are_your_nutrient_goals),
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))

            UnitTextField(
                value = viewModel.state.carbRatio,
                onValueChange = {
                    viewModel.onEvent(NutritionGoalEvent.OnCarbRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_carbs)
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))

            UnitTextField(
                value = viewModel.state.proteinRatio,
                onValueChange = {
                    viewModel.onEvent(NutritionGoalEvent.OnProteinRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_proteins)
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))

            UnitTextField(
                value = viewModel.state.fatRatio,
                onValueChange = {
                    viewModel.onEvent(NutritionGoalEvent.OnFatRatioEnter(it))
                },
                unit = stringResource(id = R.string.percent_fats)
            )
        }
        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                viewModel.onEvent(NutritionGoalEvent.OnNextClick)
            },
            modifier = Modifier.align(alignment = Alignment.BottomEnd)
        )
    }
}
