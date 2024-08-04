package com.mantequilla.jetpackcomposetemplate.domain.usecase

import com.mantequilla.jetpackcomposetemplate.data.repository.LocalRepository
import com.mantequilla.jetpackcomposetemplate.domain.item.GameDetailItem
import com.mantequilla.jetpackcomposetemplate.data.repository.Repository
import com.mantequilla.jetpackcomposetemplate.ui.detail.DetailState
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetDetailGameUseCase @Inject constructor(private val repository: Repository, private val localRepository: LocalRepository){
    operator fun invoke(id: Int): Flow<DetailState<GameDetailItem, Boolean>> = flow {
        try {
            emit(DetailState.loading())
            val detailGame = repository.getDetailGame(id)
            val isGameFavExists = localRepository.isGameFavExists(id)
            emit(DetailState.success(detailGame, isGameFavExists))

        } catch (e: Exception) {
            emit(DetailState.failure(e))
        }
    }
}