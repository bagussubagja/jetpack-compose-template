package com.mantequilla.jetpackcomposetemplate.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import kotlinx.coroutines.flow.Flow

@Dao
interface GameFavDao {
    @Query("SELECT * FROM game_table ORDER BY id DESC")
    fun getGameFav(): Flow<List<GameFav>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertGameFav(gameFav: GameFav)

    @Query("DELETE FROM game_table WHERE id = :id")
    suspend fun deleteGameFav(id: Int)

    @Query("SELECT EXISTS(SELECT 1 FROM game_table WHERE id = :id)")
    suspend fun isGameFavExists(id: Int): Boolean
}