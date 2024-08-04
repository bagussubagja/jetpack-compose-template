package com.mantequilla.jetpackcomposetemplate.domain.usecase

import com.mantequilla.jetpackcomposetemplate.data.repository.LocalRepository
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.ui.favorite.FavoriteState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGameFavUseCase @Inject constructor(private val localRepository: LocalRepository) {
    operator fun invoke(): Flow<FavoriteState<List<GameFav>>> = flow {
        try {
            emit(FavoriteState.loading())
            delay(1000)
            val gameFav = localRepository.getGameFav().first()
            emit(FavoriteState.success(gameFav))
        } catch (e: Exception) {
            emit(FavoriteState.failure(e))
        }
    }
}