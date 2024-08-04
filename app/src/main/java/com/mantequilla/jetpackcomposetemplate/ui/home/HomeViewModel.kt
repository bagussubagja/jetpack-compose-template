package com.mantequilla.jetpackcomposetemplate.ui.home

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mantequilla.jetpackcomposetemplate.domain.usecase.GetGameUseCase
import com.mantequilla.jetpackcomposetemplate.domain.item.GameItem
import com.mantequilla.jetpackcomposetemplate.ui.home.HomeState
import com.mantequilla.jetpackcomposetemplate.utils.Utils
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getGameUseCase: GetGameUseCase,
) : ViewModel() {
    private val _homeState = MutableStateFlow<HomeState<List<GameItem>>>(HomeState.loading())
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()
    val homeState: StateFlow<HomeState<List<GameItem>>> get() = _homeState

    init {
        getData()
    }

    fun getData() {
        viewModelScope.launch {
            try {
                _isLoading.value = true
                getGameUseCase().collect { result ->
                    when (result) {
                        is HomeState.SuccessFetchData -> {
                            _homeState.value = HomeState.success(result.data)
                        }
                        is HomeState.FailureFetchData -> {
                            _homeState.value = HomeState.failure(result.exception)
                        }
                        is HomeState.Loading -> {
                            // Optional: Handle loading state if needed
                        }
                    }
                }
            } catch (e: Exception) {
                _homeState.value = HomeState.failure(Exception("Failed to get data"))
            } finally {
                _isLoading.value = false
            }
        }
    }
}
