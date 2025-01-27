package com.mmag.bgamescoreboard.domain.use_cases.categories

import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetGameCategoriesUseCase @Inject constructor(
    private val scoringRepository: ScoringRepository,
) {
    suspend fun invoke(gameId: Int) = withContext(Dispatchers.IO){
        scoringRepository.getCategoriesByGameId(gameId)
    }
}