package com.example.tracker_data.local

import androidx.room.*
import com.example.tracker_data.local.entity.TrackedFoodEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Delete
    suspend fun deleteTrackedFood(trackedFoodEntity: TrackedFoodEntity)

    @Query(
        """
            SELECT *
            FROM TrackedFoodEntity
            WHERE dayOfMonth = :dayOfMonth AND month = :month AND year = :year
        """
    )
    fun getTrackedFood(dayOfMonth: Int, month: Int, year: Int): Flow<List<TrackedFoodEntity>>
}

