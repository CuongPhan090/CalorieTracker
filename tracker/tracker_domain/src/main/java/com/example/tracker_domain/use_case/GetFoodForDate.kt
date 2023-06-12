package com.example.tracker_domain.use_case

import com.example.tracker_domain.TrackerRepository
import com.example.tracker_domain.model.TrackedFood
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

class GetFoodForDate(
    private val repository: TrackerRepository
) {

    operator fun invoke(localDate: LocalDate): Flow<List<TrackedFood>> {
        return repository.getTrackedFood(localDate)
    }
}