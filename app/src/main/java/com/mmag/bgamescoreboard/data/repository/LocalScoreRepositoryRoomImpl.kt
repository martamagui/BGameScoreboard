package com.mmag.bgamescoreboard.data.repository

import com.mmag.bgamescoreboard.data.db.BGSDatabase
import javax.inject.Inject

class LocalScoreRepositoryRoomImpl @Inject constructor(
    val database: BGSDatabase
) : LocalScoreRepository {

}