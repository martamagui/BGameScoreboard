package com.mmag.bgamescoreboard.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmag.bgamescoreboard.data.db.dao.BoardGameDao
import com.mmag.bgamescoreboard.data.db.dao.PlayerDao
import com.mmag.bgamescoreboard.data.db.dao.ScoreDao
import com.mmag.bgamescoreboard.data.db.model.BoardGame

@Database(entities = [BoardGame::class], version = 1)
abstract class BGSDatabase : RoomDatabase() {
    abstract fun boardGameDao(): BoardGameDao
    abstract fun playerDao(): PlayerDao
    abstract fun scoreDao(): ScoreDao
}