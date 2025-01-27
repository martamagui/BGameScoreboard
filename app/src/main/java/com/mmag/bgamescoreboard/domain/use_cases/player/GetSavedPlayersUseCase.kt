package com.mmag.bgamescoreboard.domain.use_cases.player

import com.mmag.bgamescoreboard.data.repository.LocalPlayerRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetSavedPlayersUseCase @Inject constructor(
    private val playerRepository: LocalPlayerRepository
) {
    suspend fun invoke() = withContext(Dispatchers.IO) {
        playerRepository.getSavedPlayers()
    }
}