package com.zhangzhu95.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.zhangzhu95.core.R
import com.zhangzhu95.core.helpers.extensions.toBigPosterURL
import com.zhangzhu95.core.helpers.extensions.toDuration
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.core.ui.widgets.CastItem
import com.zhangzhu95.core.ui.widgets.Chip
import com.zhangzhu95.core.ui.widgets.FadedImage
import com.zhangzhu95.core.ui.widgets.LoadingView
import com.zhangzhu95.core.ui.widgets.Spacing
import com.zhangzhu95.core.ui.widgets.styles.AppTheme
import com.zhangzhu95.core.ui.widgets.styles.Blackish
import com.zhangzhu95.core.ui.widgets.styles.DarkerWhite
import com.zhangzhu95.data.movies.models.Actor
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MovieGenres
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    @Inject
    lateinit var navigation: DetailsNavigation

    private val viewModel: DetailsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val state =
                        viewModel.viewState.collectAsState(initial = DetailsViewState.Loading).value
                    when (state) {
                        is DetailsViewState.Loading -> LoadingView()
                        is DetailsViewState.Success -> {
                            MovieDetails(state.details, state.actors, {}) {
                                navigation.discoverMovie(requireContext(), state.details.homepage)
                            }
                        }
                        else -> Text(text = "State not handled")
                    }
                }
            }
        }
    }
}

@Composable
fun MovieDetails(
    details: MovieDetails,
    actors: List<Actor>,
    onActorClicked: (Int) -> Unit,
    onPlayClicked: () -> Unit
) {
    Box(
        contentAlignment = Alignment.TopCenter,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .background(Blackish)
    ) {
        Column {

            Box(contentAlignment = Alignment.Center) {
                // Poster
                FadedImage(
                    url = (details.backdropPath ?: details.posterPath).toBigPosterURL(),
                    placeholderRes = R.mipmap.movie_poster,
                    modifier = Modifier.height(400.dp)
                )

                // Play button
                TextButton(onClick = onPlayClicked) {
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
                    color = DarkerWhite,
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
                        icon = R.drawable.ic_baseline_star_24
                    )
                }
                Spacing.Vertical.Medium()

                // Title
                Text(details.title, fontSize = 24.sp, color = Color.White)
                Spacing.Vertical.Tiny()

                // Subtitle
                if (details.tagline.isNotEmpty()) {
                    Text(details.tagline, fontSize = 18.sp, color = DarkerWhite)
                    Spacing.Vertical.Small()
                }

                // Description
                Text(details.overview, color = DarkerWhite)
                Spacing.Vertical.Small()

                // Actors
                ActorsList(actors = actors, onActorClicked = onActorClicked)
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

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
private fun MovieDetailsPreview() {
    val text = stringResource(id = R.string.placeholder_title)
    AppTheme {
        MovieDetails(
            MovieDetails(
                title = text,
                runtime = 100,
                genres = listOf(
                    MovieGenres(name = text),
                    MovieGenres(name = text)
                ),
                voteAverage = 8.5,
                tagline = text,
                overview = stringResource(id = R.string.placeholder_description)
            ),
            actors = listOf(
                Actor(0, "Will Smith", character = "Robert Neville"),
                Actor(1, "Alice Braga", character = "Anna"),
                Actor(2, "Charlie Tahan", character = "Ethan"),
            ),
            {}, {}
        )
    }
}
