package com.dedan.rekowisatabali.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecommendationTemplate(
    val id: Int,
    @SerialName("image_url")
    val imageUrl: String?,
    val name: String,
    val description: String
)