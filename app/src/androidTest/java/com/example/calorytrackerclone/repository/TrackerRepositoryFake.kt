package com.example.calorytrackerclone.repository

import com.example.tracker_domain.TrackerRepository
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import java.time.LocalDate
import kotlin.random.Random

class TrackerRepositoryFake : TrackerRepository {

    private val trackedFood = mutableListOf<TrackedFood>()
    var searchResult = listOf<TrackableFood>()
    var shouldReturnError = false

    private val getFoodsForDateFlow =
        MutableSharedFlow<List<TrackedFood>>(replay = 1)

    override suspend fun searchFood(
        query: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return if (shouldReturnError) {
            Result.failure(Throwable())
        } else {
            Result.success(searchResult)
        }
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        this.trackedFood.add(
            trackedFood.copy(
                id = Random.nextInt()
            )
        )
        getFoodsForDateFlow.emit(this.trackedFood)

    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        this.trackedFood.remove(trackedFood)
        getFoodsForDateFlow.emit(this.trackedFood)
    }

    override fun getTrackedFood(localDate: LocalDate): Flow<List<TrackedFood>> {
        return getFoodsForDateFlow
    }
}