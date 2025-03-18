package com.mmag.bgamescoreboard.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mmag.bgamescoreboard.data.db.model.entities.ScoringCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface ScoringCategoryDao {
    @Insert
    suspend fun addCategory(scoringCategory: ScoringCategory)

    @Query("SELECT * FROM ScoringCategory WHERE game_id=:gameId")
    fun getCategoriesByGameId(gameId: Int): Flow<List<ScoringCategory>>

    @Query("DELETE FROM ScoringCategory WHERE game_id=:gameId")
    fun deleteCategoryByGame(gameId: Int):Int
}