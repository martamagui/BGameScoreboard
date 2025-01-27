package com.mmag.bgamescoreboard.domain.use_cases.game_detail

import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteGameUseCase @Inject constructor(
    private val boardGameRepository: BoardGameRepository
)  {
    suspend operator fun invoke(gameId: Int)= withContext(Dispatchers.IO){
        boardGameRepository.deleteGame(gameId)
    }
}