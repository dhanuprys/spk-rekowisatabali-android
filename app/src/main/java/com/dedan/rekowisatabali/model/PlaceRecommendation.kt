package com.dedan.rekowisatabali.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlaceRecommendation(
    val id: Int,
    val name: String,
    @SerialName("city_name")
    val cityName: String,
    val rank: Int,
    val score: Float
)

fun PlaceRecommendation.toPlaceRecommendationHistory(): PlaceRecommendationHistory =
    PlaceRecommendationHistory(
        id = id,
        name = name,
        cityName = cityName
    )