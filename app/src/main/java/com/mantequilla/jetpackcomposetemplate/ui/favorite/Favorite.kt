package com.mantequilla.jetpackcomposetemplate.ui.favorite

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.twotone.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.navigation.Screen
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenPrimary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenTertiary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greyColor
import com.mantequilla.jetpackcomposetemplate.ui.theme.greyColorDarker
import com.mantequilla.jetpackcomposetemplate.ui.theme.redSoftColor
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavoriteScreen (navController: NavHostController) {
    
    val favoriteViewModel: FavoriteViewModel = hiltViewModel()
    val favoriteState by favoriteViewModel.favoriteState.collectAsState()

    when (favoriteState) {
        is FavoriteState.FailureFetchData -> {
            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "${(favoriteState as FavoriteState.FailureFetchData).exception}")
            }
        }
        is FavoriteState.Loading -> {
            Box (modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    color = greenPrimary
                )
            }
        }
        is FavoriteState.SuccessFetchData -> {
            val gameFavData = (favoriteState as FavoriteState.SuccessFetchData<List<GameFav>>).data
            Scaffold (
                topBar = {
                    CenterAlignedTopAppBar(
                        colors = TopAppBarDefaults.smallTopAppBarColors(
                            containerColor = greenPrimary,
                            titleContentColor = greenTertiary,
                        ),
                        navigationIcon = {
                            IconButton(onClick = { navController.popBackStack() }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack,
                                    contentDescription = "Localized description", tint = greenTertiary
                                )
                            }
                        },
                        title = { Text(text = "Favorite") }
                    )
                }
            ) {
                Box (modifier = Modifier
                    .fillMaxSize()
                    .padding(it)) {
                    LazyColumn {
                        if (gameFavData.isNotEmpty()) {
                            items(gameFavData) { gameFav ->
                                FavoriteItemCard(params = gameFav, navController = navController, favoriteViewModel)
                            }    
                        } else  {
                            item {
                                Box (contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                                    Text(text = "Favorite Data Empty :(")
                                }
                            }
                        }
                        
                    }
                }
            }
        }
        }
}




@OptIn(ExperimentalFoundationApi::class)
@Composable
fun FavoriteItemCard (params: GameFav, navController: NavHostController, favoriteViewModel: FavoriteViewModel) {
    val image = rememberAsyncImagePainter(model = params.imageUrl)
    val endAction = SwipeAction(
        icon = rememberVectorPainter(Icons.TwoTone.Delete),
        background = redSoftColor,
        onSwipe = { favoriteViewModel.deleteGameFavData(params.id) },
    )
    SwipeableActionsBox (
        endActions = listOf(endAction),
        swipeThreshold = 16.dp
    ) {
        Card (
            colors = CardDefaults.cardColors(
                containerColor = greenTertiary,
                contentColor = greenPrimary,
                disabledContainerColor = greyColor,
                disabledContentColor = greyColorDarker
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                .fillMaxSize()
                .combinedClickable(
                    onClick = {
                        navController.navigate("${Screen.Detail.route}/${params.id}")
                    },
                    onLongClick = {
                        favoriteViewModel.deleteGameFavData(params.id)
                    }
                )

        ) {
            Row {
                Image(
                    painter = image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(125.dp)
                        .height(125.dp)
                )
                Box (Modifier.width(12.dp))
                Column (
                    modifier = Modifier.padding(all = 8.dp)
                ) {
                    Text(
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        text = params.name
                    )
                    Text(
                        fontWeight = FontWeight.Normal,
                        fontSize = 16.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        text = params.longDesc,
                    )
                }
                Box (Modifier.width(12.dp))
                IconButton(onClick = { favoriteViewModel.deleteGameFavData(params.id) }) {
                    Icons.Outlined.Delete
                }
            }
        }
    }
}