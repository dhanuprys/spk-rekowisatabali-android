package com.dedan.rekowisatabali.container

import com.dedan.rekowisatabali.data.NetworkPlaceRepository
import com.dedan.rekowisatabali.data.PlaceRepository
import com.dedan.rekowisatabali.network.SpkApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class DefaultAppContainer : AppContainer {
    private val baseUrl: String = "https://rekowisatabali.suryamahendra.com/api/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val retrofitService by lazy {
        retrofit.create(SpkApiService::class.java)
    }

    override val placeRepository: PlaceRepository by lazy {
        NetworkPlaceRepository(retrofitService)
    }
}