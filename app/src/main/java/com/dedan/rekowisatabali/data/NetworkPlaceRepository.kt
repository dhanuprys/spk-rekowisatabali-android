package com.dedan.rekowisatabali.data

import android.util.Log
import com.dedan.rekowisatabali.model.City
import com.dedan.rekowisatabali.model.PlaceRecommendation
import com.dedan.rekowisatabali.model.RecommendationMetric
import com.dedan.rekowisatabali.model.RecommendationTemplate
import com.dedan.rekowisatabali.model.RecommendationTemplateRequest
import com.dedan.rekowisatabali.network.SpkApiService

class NetworkPlaceRepository(
    private val apiService: SpkApiService
) : PlaceRepository {
    override suspend fun calculateManualRecomendations(
        recommendationMetric: RecommendationMetric
    ): List<PlaceRecommendation> {
        Log.d("NetworkPlaceRepository", "calculateManualRecomendations: $recommendationMetric")
        return apiService.calculateManualRecommendations(recommendationMetric)
    }

    override suspend fun calculateTemplateRecomendations(
        filter: RecommendationTemplateRequest
    ): List<PlaceRecommendation> =
        apiService.calculateTemplateRecommendations(filter)

    override suspend fun getRecommendationTemplates(): List<RecommendationTemplate> =
        apiService.getRecommendationTemplates()

    override suspend fun getCities(): List<City> = apiService.getCities()

}