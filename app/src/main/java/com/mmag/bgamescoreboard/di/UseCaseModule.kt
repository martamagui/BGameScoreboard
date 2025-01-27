package com.mmag.bgamescoreboard.di

import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.domain.use_cases.game_detail.DeleteGameUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game_detail.GetGameDetailsUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game_record.DeleteRecordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetGameDetailsUseCase(boardGameRepository: BoardGameRepository): GetGameDetailsUseCase {
        return GetGameDetailsUseCase(boardGameRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteGameUseCase(boardGameRepository: BoardGameRepository): DeleteGameUseCase {
        return DeleteGameUseCase(boardGameRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteRecordUseCase(scoringRepository: ScoringRepository): DeleteRecordUseCase {
        return DeleteRecordUseCase(scoringRepository)
    }
}