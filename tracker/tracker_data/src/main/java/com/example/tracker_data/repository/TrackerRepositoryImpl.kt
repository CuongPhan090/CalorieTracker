package com.example.tracker_data.repository

import com.example.tracker_data.local.TrackerDao
import com.example.tracker_data.mapper.toTrackableFood
import com.example.tracker_data.mapper.toTrackedFood
import com.example.tracker_data.mapper.toTrackedFoodEntity
import com.example.tracker_data.remote.OpenFoodApi
import com.example.tracker_domain.TrackerRepository
import com.example.tracker_domain.model.TrackableFood
import com.example.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate

class TrackerRepositoryImpl(
    private val dao: TrackerDao,
    private val openFoodApi: OpenFoodApi
) : TrackerRepository {
    override suspend fun searchFood(
        query: String,
        pageNumber: Int,
        pageSize: Int
    ): Result<List<TrackableFood>> {
        return try {
            val searchDto = openFoodApi.search(
                query = query,
                pageNumber = pageNumber,
                pageSize = pageSize
            )

            Result.success(searchDto.products.mapNotNull {
                it.toTrackableFood()
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Result.failure(e)
        }
    }

    override suspend fun insertTrackedFood(trackedFood: TrackedFood) {
        dao.insertTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override suspend fun deleteTrackedFood(trackedFood: TrackedFood) {
        dao.deleteTrackedFood(trackedFood.toTrackedFoodEntity())
    }

    override fun getTrackedFood(localDate: LocalDate): Flow<List<TrackedFood>> {
        return dao.getTrackedFood(
            dayOfMonth = localDate.dayOfMonth,
            month = localDate.monthValue,
            year = localDate.year
        ).map { trackFoodEntity ->
            trackFoodEntity.map {
                it.toTrackedFood()
            }
        }
    }
}
