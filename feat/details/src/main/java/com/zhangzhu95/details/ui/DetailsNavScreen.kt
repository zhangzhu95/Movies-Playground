package com.zhangzhu95.details.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhangzhu95.compose.themes.AppTheme
import com.zhangzhu95.compose.widgets.CastItem
import com.zhangzhu95.compose.widgets.Chip
import com.zhangzhu95.compose.widgets.FadedImage
import com.zhangzhu95.compose.widgets.LoadingView
import com.zhangzhu95.compose.widgets.Spacing
import com.zhangzhu95.core.helpers.extensions.openWebPage
import com.zhangzhu95.core.helpers.extensions.toBigPosterURL
import com.zhangzhu95.core.helpers.extensions.toDuration
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.data.fakes.FakeActor
import com.zhangzhu95.data.fakes.FakeMovieDetails
import com.zhangzhu95.data.movies.models.Actor
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.details.R
import com.zhangzhu95.compose.R as RC

@Composable
fun DetailsNavScreen() {
    val viewModel = hiltViewModel<DetailsViewModel>()
    val state = viewModel.viewState.collectAsState(initial = DetailsViewState.Loading).value
    when (state) {
        is DetailsViewState.Loading -> LoadingView(modifier = Modifier.fillMaxWidth())
        is DetailsViewState.Success -> {
            MovieDetails(
                state.details,
                state.actors
            )
        }
        else -> Text(text = "State not handled")
    }
}

@Composable
fun MovieDetails(
    details: MovieDetails,
    actors: List<Actor>,
    onActorClicked: ((Int) -> Unit)? = null
) {
    val context = LocalContext.current
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Column {

            Box(contentAlignment = Alignment.Center) {
                // Poster
                FadedImage(
                    url = (details.backdropPath ?: details.posterPath).toBigPosterURL(),
                    modifier = Modifier.height(400.dp)
                )

                // Play button
                TextButton(onClick = {
                    context.openWebPage(details.homepage)
                }) {
                    Image(
                        painter = painterResource(id = R.mipmap.play_button),
                        contentDescription = "",
                        modifier = Modifier.size(50.dp),
                    )
                }
            }

            Column(
                modifier = Modifier
                    .offset(y = (-40).dp)
                    .padding(horizontal = 16.dp)
            ) {

                // Duration
                Text(
                    text = details.runtime.toDuration(),
                    //color = DarkerWhite,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacing.Vertical.Medium()

                Row {
                    // Category
                    details.genres.firstOrNull()?.let {
                        Chip(text = it.name)
                        Spacing.Horizontal.Tiny()
                    }

                    // Rating
                    Chip(
                        text = details.rating,
                        icon = RC.drawable.ic_baseline_star_24
                    )
                }
                Spacing.Vertical.Medium()

                // Title
                Text(
                    details.title,
                    style = MaterialTheme.typography.h3
                )
                Spacing.Vertical.Tiny()

                // Subtitle
                if (details.tagline.isNotEmpty()) {
                    Text(
                        details.tagline,
                        style = MaterialTheme.typography.subtitle2
                    )
                    Spacing.Vertical.Small()
                }

                // Description
                Text(details.overview)
                Spacing.Vertical.Small()

                // Actors
                ActorsList(actors = actors, onActorClicked = onActorClicked ?: {})
            }
        }
    }
}

@Composable
fun ActorsList(actors: List<Actor>, onActorClicked: (Int) -> Unit) {
    LazyRow {
        items(count = actors.size, key = { actors[it].id }, itemContent = { index ->
            actors[index].apply {
                CastItem(
                    id = id,
                    name = name,
                    posterUrl = profilePath.orEmpty().toSmallPosterURL(),
                    character = character,
                    onClick = onActorClicked
                )
                Spacing.Horizontal.Tiny()
            }
        })
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3A, uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun MovieDetailsPreview() {
    AppTheme {
        MovieDetails(
            FakeMovieDetails.regularDetails,
            actors = FakeActor.listActors
        )
    }
}

