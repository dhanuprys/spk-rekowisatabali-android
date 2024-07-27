package com.dedan.rekowisatabali.data

import com.dedan.rekowisatabali.model.City
import com.dedan.rekowisatabali.model.PlaceRecommendation
import com.dedan.rekowisatabali.model.RecommendationMetric
import com.dedan.rekowisatabali.model.RecommendationTemplate
import com.dedan.rekowisatabali.model.RecommendationTemplateRequest

interface PlaceRepository {
    /**
     * Mendapatkan rekomendasi tempat wisata berdasarkan parameter
     */
    suspend fun calculateManualRecomendations(
        recommendationMetric: RecommendationMetric
    ): List<PlaceRecommendation>

    /**
     * Mendapatkan rekomendasi tempat wisata berdasarkan template
     */
    suspend fun calculateTemplateRecomendations(
        filter: RecommendationTemplateRequest
    ): List<PlaceRecommendation>

    /**
     * Mendapatkan semua template rekomendasi wisata
     */
    suspend fun getRecommendationTemplates(): List<RecommendationTemplate>

    /**
     * Mendapatkan semua nama kota/kabupaten
     */
    suspend fun getCities(): List<City>
}