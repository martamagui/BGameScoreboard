package com.mmag.bgamescoreboard.di

import com.mmag.bgamescoreboard.data.db.BGSDatabase
import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import com.mmag.bgamescoreboard.data.repository.BoardGameRepositoryRoomImpl
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepositoryRoomImpl
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.data.repository.ScoringRepositoryRoomImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDataRepositoryModule {

    @Singleton
    @Provides
    fun providesLocalScoreRepository(database: BGSDatabase): ScoringRepository {
        return ScoringRepositoryRoomImpl(database)
    }

    @Singleton
    @Provides
    fun providesLocalPlayerRepository(database: BGSDatabase): LocalPlayerRepository {
        return LocalPlayerRepositoryRoomImpl(database)
    }

    @Singleton
    @Provides
    fun providesLocalBoardGameRepository(database: BGSDatabase): BoardGameRepository {
        return BoardGameRepositoryRoomImpl(database)
    }
}