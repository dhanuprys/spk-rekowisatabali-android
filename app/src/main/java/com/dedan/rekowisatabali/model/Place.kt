package com.dedan.rekowisatabali.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "place_recommendation_history")
data class PlaceRecommendationHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    @ColumnInfo(name = "city_name")
    val cityName: String
)
