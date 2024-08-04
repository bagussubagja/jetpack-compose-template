package com.mantequilla.jetpackcomposetemplate.data.model.game_favorite

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mantequilla.jetpackcomposetemplate.utils.Constants

@Entity(tableName = Constants.GAME_TABLE)
data class GameFav(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String,
    val shortDesc: String,
    val longDesc: String,
    val imageUrl: String,
)
