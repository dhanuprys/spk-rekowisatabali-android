package com.dedan.rekowisatabali.model

import kotlinx.serialization.Serializable

@Serializable
data class RecommendationTemplateRequest(
    val limit: Int,
    val cities_id: Set<Int>,
    val template_id: Int
)