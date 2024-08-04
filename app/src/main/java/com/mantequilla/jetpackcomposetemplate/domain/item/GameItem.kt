package com.mantequilla.jetpackcomposetemplate.domain.item

import com.mantequilla.jetpackcomposetemplate.data.model.game_model.GameModel

data class GameItem(
    val id: Int?,
    val developer: String?,
    val freetogame_profile_url: String?,
    val game_url: String?,
    val genre: String?,
    val platform: String?,
    val publisher: String?,
    val release_date: String?,
    val short_description: String?,
    val thumbnail: String?,
    val title: String?
)

fun GameModel.toGameItem() = GameItem(
    id,
    developer,
    freetogame_profile_url,
    game_url,
    genre,
    platform,
    publisher,
    release_date,
    short_description,
    thumbnail,
    title,
)

