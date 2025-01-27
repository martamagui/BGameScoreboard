package com.mmag.bgamescoreboard.domain.use_cases.player

import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import com.mmag.bgamescoreboard.data.repository.ScoringRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SavePlayerUseCase @Inject constructor(
    private val playerRepository: LocalPlayerRepository,
) {

    suspend fun invoke(playerName: String) = withContext(Dispatchers.IO) {
        playerRepository.savePlayer(playerName)
    }

}