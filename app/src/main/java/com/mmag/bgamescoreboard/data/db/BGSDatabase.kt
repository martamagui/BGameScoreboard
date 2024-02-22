package com.mmag.bgamescoreboard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mmag.bgamescoreboard.data.db.dao.BoardGameDao
import com.mmag.bgamescoreboard.data.db.dao.PlayerDao
import com.mmag.bgamescoreboard.data.db.dao.ScoreDao
import com.mmag.bgamescoreboard.data.db.model.BoardGame
import com.mmag.bgamescoreboard.data.db.model.GameRecord
import com.mmag.bgamescoreboard.data.db.model.Player
import com.mmag.bgamescoreboard.data.db.model.Score
import com.mmag.bgamescoreboard.data.db.model.ScoringCategory
import com.mmag.bgamescoreboard.data.db.utils.ImageTypeConverters

@Database(
    entities = [
        BoardGame::class,
        GameRecord::class,
        Player::class,
        Score::class,
        ScoringCategory::class
    ],
    version = 1
)
@TypeConverters(ImageTypeConverters::class)
abstract class BGSDatabase : RoomDatabase() {
    abstract fun boardGameDao(): BoardGameDao
    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao
}