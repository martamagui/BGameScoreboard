package com.mmag.bgamescoreboard

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BGSApplication : Application() {

    private lateinit var db: BGSDatabase

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            BGSDatabase::class.java, "BGSDatabase"
        ).build()
    }

}