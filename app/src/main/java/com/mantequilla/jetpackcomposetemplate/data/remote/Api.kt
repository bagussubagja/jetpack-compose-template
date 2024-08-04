package com.mantequilla.jetpackcomposetemplate.data.remote

import com.mantequilla.jetpackcomposetemplate.data.model.game_detail_model.GameDetailModel
import com.mantequilla.jetpackcomposetemplate.data.model.game_model.GameModel
import com.mantequilla.jetpackcomposetemplate.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET(Constants.GAMES_ENDPOINT)
    suspend fun getGames(): Response<List<GameModel>>

    @GET(Constants.GAME_ENDPOINT)
    suspend fun getDetailGame(@Query("id") id: Int): Response<GameDetailModel>
}