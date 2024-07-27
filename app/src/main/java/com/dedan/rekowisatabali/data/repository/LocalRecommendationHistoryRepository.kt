package com.dedan.rekowisatabali.data.repository

import com.dedan.rekowisatabali.data.db.PlaceRecommendationHistoryDao
import com.dedan.rekowisatabali.model.PlaceRecommendationHistory
import kotlinx.coroutines.flow.Flow

class LocalRecommendationHistoryRepository(
    private val placeDao: PlaceRecommendationHistoryDao
) : RecommendationHistoryRepository {
    override fun getAll(): Flow<List<PlaceRecommendationHistory>> =
        placeDao.getAll()

    override suspend fun insert(place: PlaceRecommendationHistory) =
        placeDao.insert(place)

    override suspend fun deleteAll() =
        placeDao.deleteAll()

    override suspend fun overwriteAll(places: List<PlaceRecommendationHistory>) {
        placeDao.deleteAll()
        places.forEach {
            placeDao.insert(it)
        }
    }
}