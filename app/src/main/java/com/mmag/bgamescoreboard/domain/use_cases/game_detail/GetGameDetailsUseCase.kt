package com.mmag.bgamescoreboard.domain.use_cases.game_detail

import com.mmag.bgamescoreboard.data.db.model.relations.BoardGameWithGameRecordRelation
import com.mmag.bgamescoreboard.data.repository.BoardGameRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGameDetailsUseCase @Inject constructor(
    private val boardGameRepository: BoardGameRepository,
) {
    suspend operator fun invoke(gameId: Int): Flow<BoardGameWithGameRecordRelation?> =
        withContext(Dispatchers.IO) {
            boardGameRepository.getBoardGame(gameId)
        }
}