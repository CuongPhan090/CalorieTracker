package com.example.tracker_presentation.search.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.core.R
import com.example.core_ui.LocalSpacing
import com.example.tracker_presentation.components.NutrientInfo
import com.example.tracker_presentation.search.TrackableFoodUiState

@OptIn(ExperimentalCoilApi::class)
@Composable
fun TrackableFoodItem(
    trackableFoodUiState: TrackableFoodUiState,
    onClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    onTrack: () -> Unit,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current

    Column(
        modifier = modifier
            .shadow(
                elevation = 2.dp,
                shape = RoundedCornerShape(5.dp)
            )
            .background(MaterialTheme.colors.surface)
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(end = spacing.spaceSmall),
    ) {
        Row {
            Row(modifier = Modifier.weight(1f)) {
                Image(
                    painter = rememberImagePainter(
                        data = trackableFoodUiState.food.imageUrl,
                        builder = {
                            error(R.drawable.ic_burger)
                            fallback(R.drawable.ic_burger)
                        }
                    ),
                    contentDescription = trackableFoodUiState.food.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                )

                Spacer(modifier = Modifier.width(spacing.spaceMedium))

                Column(
                    modifier = Modifier.align(Alignment.CenterVertically)
                ) {
                    Text(
                        text = trackableFoodUiState.food.name,
                        style = MaterialTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )

                    Spacer(modifier = Modifier.height(spacing.spaceSmall))

                    Text(
                        text = stringResource(
                            id = R.string.kcal_per_100g,
                            trackableFoodUiState.food.caloriesPer100g
                        ),
                        style = MaterialTheme.typography.body2
                    )
                }
            }

            Row(
                modifier = Modifier.align(Alignment.CenterVertically)
            ) {
                NutrientInfo(
                    name = stringResource(id = R.string.carbs),
                    amount = trackableFoodUiState.food.carbPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameStyle = MaterialTheme.typography.body2
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                NutrientInfo(
                    name = stringResource(id = R.string.protein),
                    amount = trackableFoodUiState.food.proteinPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameStyle = MaterialTheme.typography.body2
                )

                Spacer(modifier = Modifier.width(spacing.spaceSmall))

                NutrientInfo(
                    name = stringResource(id = R.string.fat),
                    amount = trackableFoodUiState.food.fatPer100g,
                    unit = stringResource(id = R.string.grams),
                    amountTextSize = 16.sp,
                    unitTextSize = 12.sp,
                    nameStyle = MaterialTheme.typography.body2
                )
            }
        }

        AnimatedVisibility(visible = trackableFoodUiState.isExpanded) {
            Row(
                modifier = Modifier
                    .padding(spacing.spaceMedium)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row {

                    BasicTextField(
                        value = trackableFoodUiState.amount,
                        onValueChange = onAmountChange,
                        keyboardOptions = KeyboardOptions(
                            imeAction = if (trackableFoodUiState.amount.isNotBlank()) {
                                ImeAction.Done
                            } else ImeAction.Default
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                onTrack()
                                defaultKeyboardAction(ImeAction.Done)
                            }
                        ),
                        singleLine = true,
                        modifier = Modifier
                            .border(
                                shape = RoundedCornerShape(5.dp),
                                width = 0.5.dp,
                                color = MaterialTheme.colors.onSurface
                            )
                            .alignBy(LastBaseline)
                            .padding(spacing.spaceMedium)
                    )
                    Spacer(modifier = Modifier.width(spacing.spaceExtraSmall))

                    Text(
                        text = stringResource(id = R.string.grams),
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.alignBy(
                            LastBaseline
                        )
                    )
                }

                IconButton(onClick = onTrack, enabled = trackableFoodUiState.amount.isNotBlank()) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = stringResource(id = R.string.track)
                    )
                }
            }
        }
    }
}