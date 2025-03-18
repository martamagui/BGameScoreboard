package com.mmag.bgamescoreboard.domain.use_cases.player

import com.mmag.bgamescoreboard.data.db.model.subsets.FrequentPlayer
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetMostFrequentPlayersUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository,
) {
    suspend fun invoke(): List<FrequentPlayer> = withContext(Dispatchers.IO) {
        scoringRepository.getMostFrequentPlayersFromScores()
    }
}