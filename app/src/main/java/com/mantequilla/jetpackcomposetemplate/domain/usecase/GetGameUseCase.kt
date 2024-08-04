package com.mantequilla.jetpackcomposetemplate.domain.usecase

import com.mantequilla.jetpackcomposetemplate.ui.home.HomeState
import com.mantequilla.jetpackcomposetemplate.domain.item.GameItem
import com.mantequilla.jetpackcomposetemplate.data.repository.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetGameUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<HomeState<List<GameItem>>> = flow {
        try {
            emit(HomeState.loading())
            val games = repository.getGames().shuffled()
            emit(HomeState.success(games))
        } catch (e: Exception) {
            emit(HomeState.failure(e))
        }
    }
}
