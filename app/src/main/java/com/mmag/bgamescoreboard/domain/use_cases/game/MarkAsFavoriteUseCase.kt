package com.mmag.bgamescoreboard.domain.use_cases.game

import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MarkAsFavoriteUseCase @Inject constructor(
    private val boardGameRepository: BoardGameRepository,
) {
    suspend operator fun invoke(gameId: Int, isFavorite: Boolean) = withContext(Dispatchers.IO) {
        boardGameRepository.markAsFavorite(gameId, isFavorite)
    }


}