package com.dedan.rekowisatabali.container

import com.dedan.rekowisatabali.data.repository.PlaceRepository
import com.dedan.rekowisatabali.data.repository.RecommendationHistoryRepository

interface AppContainer {
    val placeRepository: PlaceRepository
    val recommendationHistoryRepository: RecommendationHistoryRepository
}