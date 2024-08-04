package com.mantequilla.jetpackcomposetemplate.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mantequilla.jetpackcomposetemplate.ui.detail.DetailScreen
import com.mantequilla.jetpackcomposetemplate.ui.favorite.FavoriteScreen
import com.mantequilla.jetpackcomposetemplate.ui.home.HomeScreen

@Composable
fun AppNavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController)
        }
        composable(route = "${Screen.Detail.route}/{gameId}", arguments = listOf(
            navArgument("gameId"){
                type = NavType.IntType
            }
        )) {
            val gameId = it?.arguments?.getInt("gameId")
            DetailScreen(navController, gameId!!)
        }
        composable(route = Screen.Favorite.route) {
            FavoriteScreen(navController)
        }
    }
}