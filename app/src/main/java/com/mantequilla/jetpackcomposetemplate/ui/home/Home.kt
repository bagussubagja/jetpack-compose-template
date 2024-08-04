package com.mantequilla.jetpackcomposetemplate.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.mantequilla.jetpackcomposetemplate.domain.item.GameItem
import com.mantequilla.jetpackcomposetemplate.navigation.Screen
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenPrimary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenSecondary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenTertiary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greyColor
import com.mantequilla.jetpackcomposetemplate.ui.theme.greyColorDarker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val homeState by homeViewModel.homeState.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val openDialog = remember { mutableStateOf(false)  }

    when (homeState) {
        is HomeState.SuccessFetchData<*> -> {
            val games = (homeState as HomeState.SuccessFetchData<List<GameItem>>).data
            if (openDialog.value) {
                AlertDialog(
                    title = {
                        Text(text = "Jetpack Compose Template")
                    },
                    text = {
                        Text("made by Bagus Subagja ❤️")
                    },
                    onDismissRequest = {},
                    confirmButton = { ElevatedButton(onClick = { openDialog.value = false }) {
                        Text(text = "Ok")
                    } })
            }
            Scaffold (
                topBar = {
                     CenterAlignedTopAppBar(
                         colors = TopAppBarDefaults.mediumTopAppBarColors(
                             containerColor = greenPrimary,
                             titleContentColor = greenTertiary,
                         ),
                         actions = { IconButton(onClick = {
                             openDialog.value = true
                         }) {
                             Icon(imageVector = Icons.Outlined.Info, contentDescription = null, tint = greenTertiary)
                         } },
                         title = { Text(text = "Game API List") }
                     )
                },
                floatingActionButton = {
                    FloatingActionButton(
                        containerColor = greenSecondary,
                        contentColor = greenTertiary,
                        onClick = { navController.navigate(Screen.Favorite.route) }
                    ) {
                        Icon(imageVector = Icons.Default.Favorite, contentDescription = "Open DB Game Fav")
                    }
                }
            ) { padding ->
                SwipeRefresh(state = swipeRefreshState, onRefresh = homeViewModel::getData) {
                    LazyColumn (modifier = Modifier.padding(padding)) {
                        items(games) { game ->
                            GameCard(game = game, navController = navController)
                        }
                    }
                }
            }
        }

        is HomeState.FailureFetchData -> {
            Text(text = "Failed to load data. Please try again.")
        }

        is HomeState.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = greenPrimary
                )
            }
        }
        else -> {
            // empty state
        }
    }
}

@Composable
fun GameCard(game: GameItem, navController: NavHostController) {
    val image = rememberAsyncImagePainter(model = game.thumbnail)
    val colors = CardDefaults.cardColors(
        disabledContainerColor = greyColor,
        containerColor = greenPrimary,
        contentColor = greenTertiary,
        disabledContentColor = greyColorDarker
    )
    Card(
        colors = colors,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
            .fillMaxSize()
            .clickable {
                navController.navigate("${Screen.Detail.route}/${game.id}")
            }
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )

        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = game.title!!, fontWeight = FontWeight.Bold)
            Text(text = game.short_description!!, maxLines = 2, overflow = TextOverflow.Ellipsis)
        }
    }
}
