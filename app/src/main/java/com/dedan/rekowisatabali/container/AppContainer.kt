package com.dedan.rekowisatabali.container

import com.dedan.rekowisatabali.data.PlaceRepository

interface AppContainer {
    val placeRepository: PlaceRepository
}