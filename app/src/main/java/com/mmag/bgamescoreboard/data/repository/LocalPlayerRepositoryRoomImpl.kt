package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.BGSDatabase
import javax.inject.Inject

class LocalPlayerRepositoryRoomImpl @Inject constructor(
    val database: BGSDatabase
) : LocalPlayerRepository {
}