package com.mmag.bgamescoreboard.di

import androidx.room.Database
import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepository
import com.mmag.bgamescoreboard.data.repository.LocalBoardGameRepositoryRoomImpl
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepositoryRoomImpl
import com.mmag.bgamescoreboard.data.repository.LocalScoreRepository
import com.mmag.bgamescoreboard.data.repository.LocalScoreRepositoryRoomImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataModule {


    @Singleton
    @Provides
    fun providesLocalScoreRepository(database: BGSDatabase): LocalScoreRepository {
        return LocalScoreRepositoryRoomImpl(database)
    }

    @Singleton
    @Provides
    fun providesLocalPlayerRepository(database: BGSDatabase): LocalPlayerRepository {
        return LocalPlayerRepositoryRoomImpl(database)
    }

    @Singleton
    @Provides
    fun providesLocalBoardGameRepository(database: BGSDatabase): LocalBoardGameRepository {
        return LocalBoardGameRepositoryRoomImpl(database)
    }
}