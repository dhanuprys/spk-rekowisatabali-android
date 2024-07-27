package com.dedan.rekowisatabali.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.dedan.rekowisatabali.model.PlaceRecommendationHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceRecommendationHistoryDao {
    @Query("SELECT * FROM place_recommendation_history")
    fun getAll(): Flow<List<PlaceRecommendationHistory>>

    @Insert
    suspend fun insert(place: PlaceRecommendationHistory)

    @Query("DELETE FROM place_recommendation_history")
    suspend fun deleteAll()
}