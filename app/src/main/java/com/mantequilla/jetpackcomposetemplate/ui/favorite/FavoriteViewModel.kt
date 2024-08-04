package com.mantequilla.jetpackcomposetemplate.ui.favorite

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantequilla.jetpackcomposetemplate.data.repository.LocalRepository
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.domain.usecase.GetGameFavUseCase
import com.mantequilla.jetpackcomposetemplate.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val localRepository: LocalRepository,
    private val getGameFavUseCase: GetGameFavUseCase,
    private val application: Application,
    ) : ViewModel() {
    private val _favoriteState = MutableStateFlow<FavoriteState<List<GameFav>>>(FavoriteState.loading())
    val favoriteState: StateFlow<FavoriteState<List<GameFav>>> get() = _favoriteState

    init {
        getGameFavData()
    }

    override fun onCleared() {
        getGameFavData()
        super.onCleared()
    }

    fun deleteGameFavData(id: Int) {
        viewModelScope.launch {
            localRepository.deleteGameFav(id)
        }
        Utils.showToast(application.applicationContext, "Favorite game removed!")
        getGameFavData()
    }
    private fun getGameFavData() {
        viewModelScope.launch {
            getGameFavUseCase().collect() { result ->
                when(result) {
                    is FavoriteState.FailureFetchData -> {
                        _favoriteState.value = FavoriteState.failure(result.exception)
                        Utils.showToast(application.applicationContext, "Failed to fetch favorite Data")
                    }
                    is FavoriteState.Loading -> {
                        // handling loading state if needed
                    }
                    is FavoriteState.SuccessFetchData -> {
                        _favoriteState.value = FavoriteState.success(result.data)
                    }
                }
            }
        }
    }
}