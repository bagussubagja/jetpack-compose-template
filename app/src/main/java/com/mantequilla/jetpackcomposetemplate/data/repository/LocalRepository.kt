package com.mantequilla.jetpackcomposetemplate.data.repository

import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    fun getGameFav(): Flow<List<GameFav>>

    suspend fun insertGameFav(gameFav: GameFav): Unit

    suspend fun deleteGameFav(id: Int): Unit

    suspend fun isGameFavExists(id: Int): Boolean
}