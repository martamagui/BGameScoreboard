package com.mmag.bgamescoreboard.domain.use_cases.scores

import com.mmag.bgamescoreboard.data.db.model.relations.RecordWithCategories
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetScoresWithPlayersByCategoryUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository,
) {
    suspend fun invoke(
        record: RecordWithCategories,
        categoryId: Int,
    ) = withContext(Dispatchers.IO) {
        scoringRepository.getScoresWithPlayersByCategory(record.record.id, categoryId)
    }

}