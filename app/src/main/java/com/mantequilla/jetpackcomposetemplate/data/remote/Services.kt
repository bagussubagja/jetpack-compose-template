package com.mantequilla.jetpackcomposetemplate.data.remote

import com.mantequilla.jetpackcomposetemplate.data.model.game_detail_model.GameDetailModel
import com.mantequilla.jetpackcomposetemplate.data.model.game_model.GameModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Services @Inject constructor(private val api: Api) {
    suspend fun getGames(): List<GameModel> {
        return withContext(Dispatchers.IO) {
            val games = api.getGames()
            games.body() ?: emptyList()
        }
    }

    suspend fun getDetailGame(id: Int): GameDetailModel {
        return withContext(Dispatchers.IO) {
            val detailGame = api.getDetailGame(id)
            detailGame.body()!!
        }
    }
}
