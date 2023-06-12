package com.example.tracker_domain.use_case

import com.example.tracker_domain.TrackerRepository
import com.example.tracker_domain.model.TrackableFood

class SearchFood(
    private val repository: TrackerRepository
) {

    suspend operator fun invoke(
        query: String,
        pageNumber: Int = 1,
        pageSize: Int = 40
    ): Result<List<TrackableFood>> {
        if (query.isBlank()) {
            return Result.success(emptyList())
        }

        return repository.searchFood(
            query = query.trim(),
            pageNumber = pageNumber,
            pageSize = pageSize
        )
    }
}
