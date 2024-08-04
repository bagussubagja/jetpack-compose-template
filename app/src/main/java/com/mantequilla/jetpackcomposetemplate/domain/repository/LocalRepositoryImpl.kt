package com.mantequilla.jetpackcomposetemplate.domain.repository

import com.mantequilla.jetpackcomposetemplate.data.local.dao.GameFavDao
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.data.repository.LocalRepository

class LocalRepositoryImpl (private val gameFavDao: GameFavDao) : LocalRepository {
    override fun getGameFav() = gameFavDao.getGameFav()
    override suspend fun insertGameFav(gameFav: GameFav): Unit = gameFavDao.insertGameFav(gameFav)
    override suspend fun deleteGameFav(id: Int) : Unit = gameFavDao.deleteGameFav(id)
    override suspend fun isGameFavExists(id: Int): Boolean = gameFavDao.isGameFavExists(id)
}