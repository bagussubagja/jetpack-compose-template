package com.mantequilla.jetpackcomposetemplate.ui.home

import com.mantequilla.jetpackcomposetemplate.domain.item.GameItem

sealed class HomeState<out T> {
    data class SuccessFetchData<out T>(val data: T) : HomeState<T>()
    data class FailureFetchData(val exception: Exception) : HomeState<Nothing>()
    object Loading : HomeState<Nothing>()

    companion object {
        fun <T> success(data: T): SuccessFetchData<T> = SuccessFetchData(data)
        fun failure(exception: Exception): FailureFetchData = FailureFetchData(exception)
        fun loading(): Loading = Loading
    }

    fun getGameItemOrNull(): GameItem? {
        return when (this) {
            is SuccessFetchData -> data as? GameItem
            else -> null
        }
    }

    fun exceptionOrNull(): Exception? {
        return when (this) {
            is FailureFetchData -> exception
            else -> null
        }
    }
}
