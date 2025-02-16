package com.mmag.bgamescoreboard

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.migrations.MIGRATION_1_2
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BGSApplication : Application() {
}