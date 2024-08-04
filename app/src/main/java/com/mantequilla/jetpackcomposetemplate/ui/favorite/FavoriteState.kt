package com.mantequilla.jetpackcomposetemplate.ui.favorite

import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.domain.item.GameDetailItem

sealed class FavoriteState<out T> {
    data class SuccessFetchData<out T>(val data: T): FavoriteState<T>()
    data class FailureFetchData(val exception: Exception) : FavoriteState<Nothing>()
    object Loading : FavoriteState<Nothing>()

    companion object {
        fun <T> success(data: T): SuccessFetchData<T> = SuccessFetchData(data)
        fun failure(exception: Exception): FailureFetchData = FailureFetchData(exception)
        fun loading(): Loading = Loading
    }

    fun getGameFavItemsOrNull(): List<GameFav>? {
        return when (this) {
            is SuccessFetchData -> data as List<GameFav>
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