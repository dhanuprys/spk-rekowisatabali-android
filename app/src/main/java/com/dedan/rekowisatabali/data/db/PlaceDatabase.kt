package com.dedan.rekowisatabali.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dedan.rekowisatabali.model.PlaceRecommendationHistory

@Database(entities = [PlaceRecommendationHistory::class], version = 1, exportSchema = false)
abstract class PlaceDatabase : RoomDatabase() {
    abstract fun getPlaceDao(): PlaceRecommendationHistoryDao

    companion object {
        const val DATABASE_NAME = "place_database"

        @Volatile
        private var Instance: PlaceDatabase? = null

        fun getDatabase(context: Context): PlaceDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                        context,
                        PlaceDatabase::class.java,
                        DATABASE_NAME
                    )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}