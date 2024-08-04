package com.mantequilla.jetpackcomposetemplate.domain.item

import com.mantequilla.jetpackcomposetemplate.data.model.game_detail_model.GameDetailModel
import com.mantequilla.jetpackcomposetemplate.data.model.game_detail_model.MinimumSystemRequirements
import com.mantequilla.jetpackcomposetemplate.data.model.game_detail_model.Screenshot

data class GameDetailItem(
    val description: String?,
    val developer: String?,
    val freetogame_profile_url: String?,
    val game_url: String?,
    val genre: String?,
    val id: Int?,
    val minimum_system_requirements: MinimumSystemRequirements?,
    val platform: String?,
    val publisher: String?,
    val release_date: String?,
    val screenshots: List<Screenshot?>?,
    val short_description: String?,
    val status: String?,
    val thumbnail: String?,
    val title: String?
)

fun GameDetailModel.toGameDetailItem() = GameDetailItem(
    description,
    developer,
    freetogame_profile_url,
    game_url,
    genre,
    id,
    minimum_system_requirements,
    platform,
    publisher,
    release_date,
    screenshots,
    short_description,
    status,
    thumbnail,
    title
)