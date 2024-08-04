package com.mantequilla.jetpackcomposetemplate.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.mantequilla.jetpackcomposetemplate.data.model.game_favorite.GameFav
import com.mantequilla.jetpackcomposetemplate.domain.item.GameDetailItem
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenPrimary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenSecondary
import com.mantequilla.jetpackcomposetemplate.ui.theme.greenTertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavHostController, id: Int) {
    val detailViewModel: DetailViewModel = hiltViewModel()
    val detailState by detailViewModel.detailState.collectAsState()
    val horizontalScrollState = rememberScrollState()
    val verticalScrollState = rememberScrollState()

    Box(modifier = Modifier.fillMaxSize()) {
        when (detailState) {
            is DetailState.SuccessFetchData -> {
                val detailGame = (detailState as DetailState.SuccessFetchData<GameDetailItem, Boolean>).data
                val isGameFavExist = (detailState as DetailState.SuccessFetchData<GameDetailItem, Boolean>).result
                val image = rememberAsyncImagePainter(model = detailGame.thumbnail)
                val imagePainters: List<Painter> = detailGame.screenshots?.map { screenshot ->
                    rememberAsyncImagePainter(model = screenshot?.image)
                } ?: emptyList()

                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            colors = TopAppBarDefaults.smallTopAppBarColors(
                                containerColor = greenPrimary,
                                titleContentColor = greenTertiary,
                            ),
                            title = {
                                Text(detailGame.title!!, maxLines = 1, overflow = TextOverflow.Ellipsis)
                            },
                            actions = {
                                      IconButton(onClick = {
                                          val params = GameFav(id = detailGame.id!!, name = detailGame.title!!, shortDesc = detailGame.short_description!!, longDesc = detailGame.description!!, imageUrl = detailGame.thumbnail!!)
                                          if (isGameFavExist) {
                                              detailViewModel.deleteGameFav(params.id, params.name)
                                          } else {
                                              detailViewModel.insertGameFav(gameFav = params)
                                          }
                                      }) {
                                          Icon(imageVector = if (isGameFavExist) Icons.Default.Favorite else Icons.Outlined.FavoriteBorder, contentDescription = "Favorite Button", tint = greenTertiary)
                                      }
                            },
                            navigationIcon = {
                                IconButton(onClick = { navController.popBackStack() }) {
                                    Icon(
                                        imageVector = Icons.Default.ArrowBack,
                                        contentDescription = "Localized description",
                                        tint = greenTertiary
                                    )
                                }
                            },
                        )
                    },
                ) { padding ->
                    Column(
                        Modifier
                            .verticalScroll(verticalScrollState)
                            .padding(padding)) {
                        Image(
                            painter = image,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(250.dp)
                        )
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = detailGame.title!!,
                                style = TextStyle(fontSize = 24.sp),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = detailGame.short_description ?: "-",
                                style = TextStyle(fontSize = 16.sp),
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Row(
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = detailGame.publisher ?: "-",
                                    style = TextStyle(fontSize = 16.sp),
                                    fontWeight = FontWeight.Normal
                                )
                                Text(
                                    text = detailGame.release_date ?: "-",
                                    style = TextStyle(fontSize = 16.sp),
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Screenshot",
                                style = TextStyle(fontSize = 24.sp),
                                fontWeight = FontWeight.Medium
                            )
                            Row(modifier = Modifier.horizontalScroll(horizontalScrollState)) {
                                for (ss in imagePainters) {
                                    Image(
                                        painter = ss,
                                        contentDescription = null,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier
                                            .width(200.dp)
                                            .height(200.dp)
                                            .padding(6.dp)
                                            .clip(shape = RoundedCornerShape(12.dp))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Minimun Specification",
                                style = TextStyle(fontSize = 24.sp),
                                fontWeight = FontWeight.Medium
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = detailGame.minimum_system_requirements?.os ?: "-",
                                style = TextStyle(fontSize = 16.sp),
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = detailGame.minimum_system_requirements?.processor ?: "-",
                                style = TextStyle(fontSize = 16.sp),
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = detailGame.minimum_system_requirements?.memory ?: "-",
                                style = TextStyle(fontSize = 16.sp),
                                fontWeight = FontWeight.Normal
                            )
                            Text(
                                text = detailGame.minimum_system_requirements?.graphics ?: "-",
                                style = TextStyle(fontSize = 16.sp),
                                fontWeight = FontWeight.Normal
                            )
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "Description",
                                style = TextStyle(fontSize = 24.sp),
                                fontWeight = FontWeight.Medium
                            )
                            Text(
                                text = detailGame.description ?: "no description available",
                                style = TextStyle(fontSize = 16.sp),
                                fontWeight = FontWeight.Normal
                            )

                        }
                    }
                }
            }
            is DetailState.FailureFetchData -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Failed to load data. Please try again.")
                }
            }
            is DetailState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        color = greenSecondary
                    )
                }
            }
        }
    }
}
