package com.mmag.bgamescoreboard.domain.use_cases.game_record

import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetRecordsCountUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository
) {
    suspend fun invoke() = withContext(Dispatchers.IO){
        scoringRepository.getRecordsCount()
    }
}