package com.dedan.rekowisatabali.data.repository

import com.dedan.rekowisatabali.model.PlaceRecommendationHistory
import kotlinx.coroutines.flow.Flow

interface RecommendationHistoryRepository {
    fun getAll(): Flow<List<PlaceRecommendationHistory>>
    suspend fun insert(place: PlaceRecommendationHistory)
    suspend fun deleteAll()
    suspend fun overwriteAll(places: List<PlaceRecommendationHistory>)
}