package com.mmag.bgamescoreboard.di

import android.content.Context
import androidx.room.Room
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): BGSDatabase {
        return Room.databaseBuilder(context, BGSDatabase::class.java, "BGSDatabase").build()
    }
}