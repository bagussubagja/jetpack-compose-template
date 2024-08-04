package com.mantequilla.jetpackcomposetemplate.data.model.game_model

data class GameModel(
    val id: Int,
    val developer: String,
    val freetogame_profile_url: String,
    val game_url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val release_date: String,
    val short_description: String,
    val thumbnail: String,
    val title: String
)

