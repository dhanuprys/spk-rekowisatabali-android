package com.dedan.rekowisatabali.model

import kotlinx.serialization.Serializable

@Serializable
data class PlaceRecommendation(
    val id: Int,
    val name: String,
    val rank: Int,
    val score: Float
)
