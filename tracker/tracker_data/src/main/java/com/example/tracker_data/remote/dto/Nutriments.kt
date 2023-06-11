package com.example.tracker_data.remote.dto

import com.squareup.moshi.Json

data class Nutriments(
    @Json(name = "carbohydrates_100g" )
    val carb100g: Double,
    @Json(name = "proteins_100g")
    val protein100g: Double,
    @Json(name = "fat_100g")
    val fat100g: Double,
    @Json(name = "energy-kcal_100g")
    val energyKcal100g: Double
)
