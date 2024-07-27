package com.dedan.rekowisatabali.container

import android.content.Context
import com.dedan.rekowisatabali.data.db.PlaceDatabase
import com.dedan.rekowisatabali.data.repository.LocalRecommendationHistoryRepository
import com.dedan.rekowisatabali.data.repository.NetworkPlaceRepository
import com.dedan.rekowisatabali.data.repository.PlaceRepository
import com.dedan.rekowisatabali.data.repository.RecommendationHistoryRepository
import com.dedan.rekowisatabali.network.SpkApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class DefaultAppContainer(context: Context) : AppContainer {
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

    override val recommendationHistoryRepository: RecommendationHistoryRepository by lazy {
        val database = PlaceDatabase.getDatabase(context).getPlaceDao()
        LocalRecommendationHistoryRepository(database)
    }
}