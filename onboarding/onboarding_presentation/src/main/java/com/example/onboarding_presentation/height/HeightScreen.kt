package com.example.onboarding_presentation.height

import android.content.Context
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.components.ActionButton
import com.example.components.UnitTextField
import com.example.core.util.UiEvent
import com.example.core.R
import com.example.core_ui.LocalSpacing
import kotlinx.coroutines.flow.collect

@Composable
fun HeightScreen(
    onNextClick: () -> Unit,
    scaffoldState: ScaffoldState,
    viewModel: HeightViewModel = hiltViewModel(),
) {
    val context: Context = LocalContext.current

    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Success -> onNextClick()
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
                text = stringResource(id = R.string.whats_your_height),
                style = MaterialTheme.typography.h3
            )

            Spacer(modifier = Modifier.height(LocalSpacing.current.spaceMedium))

            Row {
                UnitTextField(
                    value = viewModel.height,
                    onValueChange = viewModel::onHeightEnter,
                    unit = stringResource(id = R.string.cm)
                )
            }
        }

        ActionButton(
            text = stringResource(id = R.string.next),
            onClick = {
                viewModel.onNextClick()
            },
            modifier = Modifier.align(Alignment.BottomEnd)
        )
    }
}
