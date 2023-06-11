package com.example.tracker_data.mapper

import com.example.tracker_data.remote.dto.Product
import com.example.tracker_domain.model.TrackableFood
import kotlin.math.roundToInt

fun Product.toTrackableFood(): TrackableFood? {
    val caloriesPer100g = nutriment.energyKcal100g.roundToInt()
    val carbPer100g = nutriment.carb100g.roundToInt()
    val proteinPer100g = nutriment.protein100g.roundToInt()
    val fatPer100g = nutriment.fat100g.roundToInt()

    return TrackableFood(
        name = productName ?: return null,
        imageUrl = imageUrl,
        caloriesPer100g = caloriesPer100g,
        carbPer100g = carbPer100g,
        proteinPer100g = proteinPer100g,
        fatPer100g = fatPer100g
    )
}
