package com.mantequilla.jetpackcomposetemplate.ui.detail

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantequilla.jetpackcomposetemplate.data.repository.LocalRepository
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.domain.usecase.GetDetailGameUseCase
import com.mantequilla.jetpackcomposetemplate.domain.item.GameDetailItem
import com.mantequilla.jetpackcomposetemplate.utils.Utils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.Closeable
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getDetailGameUseCase: GetDetailGameUseCase,
    savedStateHandle: SavedStateHandle,
    private val localRepository: LocalRepository,
    private val application: Application
) : ViewModel() {

    private val _detailState = MutableStateFlow<DetailState<GameDetailItem, Boolean>>(DetailState.loading())
    val detailState: StateFlow<DetailState<GameDetailItem, Boolean>> get() = _detailState

    private val gameIdParam = savedStateHandle.get<Int>("gameId")!!

    init {
        getDataGameDetail(gameIdParam)
    }

    override fun onCleared() {
        super.onCleared()
    }

    override fun addCloseable(closeable: Closeable) {
        super.addCloseable(closeable)
    }

    fun deleteGameFav(id: Int, name: String) {
        viewModelScope.launch {
            localRepository.deleteGameFav(id)
            Utils.showToast(context = application.applicationContext, message = "$name deleted from favorite!")
        }
        getDataGameDetail(id)
    }

    fun insertGameFav(gameFav: GameFav) {
        viewModelScope.launch {
            localRepository.insertGameFav(gameFav)
            Utils.showToast(context = application.applicationContext, message = "Success Add ${gameFav.name} to Favorite!")
        }
        getDataGameDetail(gameFav.id)
    }

    private fun getDataGameDetail(id: Int) {
        viewModelScope.launch {
            try {
                getDetailGameUseCase(id).collect { result ->
                    when (result) {
                        is DetailState.SuccessFetchData -> {
                            _detailState.value = DetailState.success(result.data, result.result)
                        }
                        is DetailState.FailureFetchData -> {
                            _detailState.value = DetailState.failure(result.exception)
                        }
                        is DetailState.Loading -> {
                            // Optional: Handle loading state if needed
                        }
                    }
                }
            } catch (e: Exception) {
                _detailState.value = DetailState.failure(Exception("Failed to get detail data"))
            }
        }
    }
}
