package com.mmag.bgamescoreboard.domain.use_cases.categories

import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SaveCategoryUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository
) {
    suspend operator fun invoke(gameId:Int, categoryName: String) = withContext(Dispatchers.IO) {
        scoringRepository.addCategory(gameId, categoryName)
    }
}