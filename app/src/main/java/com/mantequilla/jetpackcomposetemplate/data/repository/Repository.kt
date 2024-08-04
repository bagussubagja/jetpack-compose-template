package com.mantequilla.jetpackcomposetemplate.data.repository

import com.mantequilla.jetpackcomposetemplate.data.remote.Services
import com.mantequilla.jetpackcomposetemplate.domain.item.GameDetailItem
import com.mantequilla.jetpackcomposetemplate.domain.item.GameItem
import com.mantequilla.jetpackcomposetemplate.domain.item.toGameDetailItem
import com.mantequilla.jetpackcomposetemplate.domain.item.toGameItem
import javax.inject.Inject

class Repository @Inject constructor(private val services: Services){
    suspend fun getGames(): List<GameItem>{
        return services.getGames().map {
            it.toGameItem()
        }
    }

    suspend fun getDetailGame(id: Int): GameDetailItem {
        return services.getDetailGame(id).toGameDetailItem()
    }
}