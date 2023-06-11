package com.example.tracker_data.remote.dto

import com.squareup.moshi.Json

data class Product (
    @Json(name = "image_front_thumb_url")
    val imageUrl: String?,
    @Json(name = "product_name")
    val productName: String?,
    val nutriment: Nutriments
)