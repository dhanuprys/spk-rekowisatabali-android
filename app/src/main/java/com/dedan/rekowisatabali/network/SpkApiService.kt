package com.dedan.rekowisatabali.network

import com.dedan.rekowisatabali.model.City
import com.dedan.rekowisatabali.model.PlaceRecommendation
import com.dedan.rekowisatabali.model.RecommendationMetric
import com.dedan.rekowisatabali.model.RecommendationTemplateRequest
import com.dedan.rekowisatabali.model.RecommendationTemplate
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SpkApiService {
    @POST("v1/calculate-manual")
    suspend fun calculateManualRecommendations(
        @Body recommendationMetric: RecommendationMetric
    ): List<PlaceRecommendation>

    @POST("v1/calculate-template")
    suspend fun calculateTemplateRecommendations(
        @Body templateRequest: RecommendationTemplateRequest
    ): List<PlaceRecommendation>

    @GET("v1/recommendation-template")
    suspend fun getRecommendationTemplates(): List<RecommendationTemplate>

    @GET("v1/city")
    suspend fun getCities(): List<City>
}