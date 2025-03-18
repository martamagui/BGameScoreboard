package com.mmag.bgamescoreboard.domain.use_cases.game

import com.mmag.bgamescoreboard.data.db.model.entities.BoardGame
import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EditGameUseCase @Inject constructor(
    private val boardGameRepository: BoardGameRepository,
) {
    suspend fun invoke(game: BoardGame) = withContext(Dispatchers.IO) {
        boardGameRepository.updateGame(game)
    }
}