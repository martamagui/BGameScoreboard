package com.mmag.bgamescoreboard

import android.app.Application
import android.os.StrictMode
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.db.migrations.MIGRATION_1_2
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BGSApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    //.penaltyDeath()
                    .build()
            )

            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder()
                    .detectLeakedClosableObjects()
                    .detectLeakedSqlLiteObjects()
                    .penaltyLog()
                    .build()
            )
        }
    }
}