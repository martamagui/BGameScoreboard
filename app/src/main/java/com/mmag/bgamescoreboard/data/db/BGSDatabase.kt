package com.mmag.bgamescoreboard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mmag.bgamescoreboard.data.db.dao.BoardGameDao
import com.mmag.bgamescoreboard.data.db.dao.PlayerDao
import com.mmag.bgamescoreboard.data.db.dao.RecordDao
import com.mmag.bgamescoreboard.data.db.dao.ScoreDao
import com.mmag.bgamescoreboard.data.db.dao.ScoringCategoryDao
import com.mmag.bgamescoreboard.data.db.model.entities.BoardGame
import com.mmag.bgamescoreboard.data.db.model.entities.GameScoreRecord
import com.mmag.bgamescoreboard.data.db.model.entities.Player
import com.mmag.bgamescoreboard.data.db.model.entities.Score
import com.mmag.bgamescoreboard.data.db.model.entities.ScoringCategory
import com.mmag.bgamescoreboard.data.db.utils.ImageTypeConverters

@Database(
    entities = [
        BoardGame::class,
        GameScoreRecord::class,
        Player::class,
        Score::class,
        ScoringCategory::class
    ],
    version = 3
)
@TypeConverters(ImageTypeConverters::class)
abstract class BGSDatabase : RoomDatabase() {
    abstract fun boardGameDao(): BoardGameDao
    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao
    abstract fun categoryDao(): ScoringCategoryDao
    abstract fun recordDao(): RecordDao
}