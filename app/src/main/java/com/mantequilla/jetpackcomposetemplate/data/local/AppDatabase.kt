package com.mantequilla.jetpackcomposetemplate.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mantequilla.jetpackcomposetemplate.data.local.dao.GameFavDao
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav

@Database(
    entities = [GameFav::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {
    abstract val dao: GameFavDao
}
