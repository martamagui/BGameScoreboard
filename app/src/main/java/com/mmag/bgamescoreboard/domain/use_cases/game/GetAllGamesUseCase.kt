package com.mmag.bgamescoreboard.domain.use_cases.game

import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetAllGamesUseCase @Inject constructor(
    private val boardGameRepository: BoardGameRepository
) {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        boardGameRepository.getAllBoardGames()
    }
}