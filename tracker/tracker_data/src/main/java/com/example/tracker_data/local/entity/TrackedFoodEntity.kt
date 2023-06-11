package com.example.tracker_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TrackedFoodEntity (
    val name: String,
    val imageUrl: String,
    val carbs: Int,
    val protein: Int,
    val fat: Int,
    val type: String,
    val amount: Int,
    val dayOfMonth: Int,
    val week: Int,
    val year: Int,
    val calories: Int,
    @PrimaryKey val id: Int? = null
)
