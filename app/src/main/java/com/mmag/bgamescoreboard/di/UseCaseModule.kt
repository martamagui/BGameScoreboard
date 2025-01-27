package com.mmag.bgamescoreboard.di

import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import com.mmag.bgamescoreboard.domain.use_cases.categories.GetGameCategoriesUseCase
import com.mmag.bgamescoreboard.domain.use_cases.categories.SaveCategoryUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.DeleteGameUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.GetAllGamesUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game.GetGameDetailsUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game_record.DeleteRecordUseCase
import com.mmag.bgamescoreboard.domain.use_cases.game_record.GetRecordsCountUseCase
import com.mmag.bgamescoreboard.domain.use_cases.player.GetSavedPlayersUseCase
import com.mmag.bgamescoreboard.domain.use_cases.player.SavePlayerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    //region Game
    @Provides
    @Singleton
    fun provideGetAllGamesUseCase(boardGameRepository: BoardGameRepository): GetAllGamesUseCase {
        return GetAllGamesUseCase(boardGameRepository)
    }

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
    //endregion Game

    //region GameRecord
    @Provides
    @Singleton
    fun provideGetRecordsCountUseCase(scoringRepository: ScoringRepository): GetRecordsCountUseCase {
        return GetRecordsCountUseCase(scoringRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteRecordUseCase(scoringRepository: ScoringRepository): DeleteRecordUseCase {
        return DeleteRecordUseCase(scoringRepository)
    }
    //endregion GameRecord

    //region Category
    @Provides
    @Singleton
    fun provideSaveCategoryUseCase(scoringRepository: ScoringRepository): SaveCategoryUseCase {
        return SaveCategoryUseCase(scoringRepository)
    }

    @Provides
    @Singleton
    fun provideGetGameCategoriesUseCase(scoringRepository: ScoringRepository): GetGameCategoriesUseCase {
        return GetGameCategoriesUseCase(scoringRepository)
    }
    //endregion Category

    //region Player
    @Provides
    @Singleton
    fun provideSavePlayerUseCase(playerRepository: LocalPlayerRepository): SavePlayerUseCase {
        return SavePlayerUseCase(playerRepository)
    }

    @Provides
    @Singleton
    fun provideGetSavedPlayersUseCas(playerRepository: LocalPlayerRepository): GetSavedPlayersUseCase {
        return GetSavedPlayersUseCase(playerRepository)
    }
    //endregion Player
}