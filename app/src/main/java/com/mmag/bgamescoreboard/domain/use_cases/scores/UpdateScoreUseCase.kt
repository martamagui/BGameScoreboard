package com.mmag.bgamescoreboard.domain.use_cases.scores

import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UpdateScoreUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository,
) {
    suspend fun invoke(score: Score) = withContext(Dispatchers.IO) {
        scoringRepository.updateScore(score)
    }
}