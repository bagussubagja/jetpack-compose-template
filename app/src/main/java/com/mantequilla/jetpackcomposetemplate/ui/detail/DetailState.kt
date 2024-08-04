package com.mantequilla.jetpackcomposetemplate.ui.detail

import com.mantequilla.jetpackcomposetemplate.domain.item.GameDetailItem

sealed class DetailState<out T, out U> {
    data class SuccessFetchData<T, U>(val data: T, val result: U) : DetailState<T, U>()
    data class FailureFetchData(val exception: Exception) : DetailState<Nothing, Nothing>()
    object Loading : DetailState<Nothing, Nothing>()

    companion object {
        fun <T, U> success(data: T, result: U): SuccessFetchData<T, U> = SuccessFetchData(data, result)
        fun failure(exception: Exception): FailureFetchData = FailureFetchData(exception)
        fun loading(): Loading = Loading
    }

    fun getDataOrNull(): T? {
        return when (this) {
            is SuccessFetchData -> data
            else -> null
        }
    }

    fun getResultOrNull(): U? {
        return when (this) {
            is SuccessFetchData -> result
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