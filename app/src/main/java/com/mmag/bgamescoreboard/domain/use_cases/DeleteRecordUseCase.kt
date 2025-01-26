package com.mmag.bgamescoreboard.domain.use_cases

import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteRecordUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository
) {
    suspend operator fun invoke(recordId: Int) = withContext(Dispatchers.IO){
        scoringRepository.deleteRecordAndScores(recordId)
    }
}