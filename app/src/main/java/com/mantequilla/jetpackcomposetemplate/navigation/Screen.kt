package com.mantequilla.jetpackcomposetemplate.navigation

sealed class Screen (val route: String) {
    object Home: Screen("home")
    object Detail: Screen("detail")
    object Favorite: Screen("favorite")
}
