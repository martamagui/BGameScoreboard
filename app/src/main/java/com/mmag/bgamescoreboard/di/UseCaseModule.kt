package com.mmag.bgamescoreboard.di

import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.domain.use_cases.DeleteRecordUseCase
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
    fun provideDeleteRecordUseCase(scoringRepository: ScoringRepository): DeleteRecordUseCase {
        return DeleteRecordUseCase(scoringRepository)
    }
}