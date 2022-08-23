package com.zhangzhu95.details.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.zhangzhu95.core.data.networking.details.MovieDetails
import com.zhangzhu95.core.data.networking.details.MovieGenres
import com.zhangzhu95.core.helpers.extensions.toDuration
import com.zhangzhu95.core.helpers.extensions.toPosterURL
import com.zhangzhu95.core.ui.compose.AppTheme
import com.zhangzhu95.core.ui.compose.Blackish
import com.zhangzhu95.core.ui.compose.Chip
import com.zhangzhu95.core.ui.compose.DarkerWhite
import com.zhangzhu95.core.ui.compose.FadedImage
import com.zhangzhu95.core.ui.widgets.LoadingView
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
                        is DetailsViewState.Success -> movieDetails(state.data) {
                            navigation.discoverMovie(requireContext(), state.data.homepage)
                        }
                        else -> Text(text = "State not handled")
                    }
                }
            }
        }
    }
}

@Composable
fun movieDetails(details: MovieDetails, onPlayClicked: () -> Unit) {
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
                    url = details.backdrop_path.toPosterURL(),
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
                Spacer(modifier = Modifier.padding(bottom = 16.dp))

                // Categories
                LazyRow {
                    items(details.genres) { genre ->
                        Chip(text = genre.name)
                        Spacer(modifier = Modifier.padding(horizontal = 5.dp))
                    }
                }
                Spacer(modifier = Modifier.padding(bottom = 24.dp))

                // Title
                Text(details.title, fontSize = 24.sp, color = Color.White)
                Spacer(modifier = Modifier.padding(bottom = 4.dp))

                // Subtitle
                if (details.tagline.isNotEmpty()) {
                    Text(details.tagline, fontSize = 18.sp, color = DarkerWhite)
                    Spacer(modifier = Modifier.padding(bottom = 12.dp))
                }

                // Description
                Text(details.overview, color = DarkerWhite)
            }
        }
    }
}

@Preview(showBackground = true, device = Devices.PIXEL_3A)
@Composable
fun previewMovieDetails() {
    val text = stringResource(id = R.string.placeholder_title)
    AppTheme {
        movieDetails(
            MovieDetails(
                title = text,
                runtime = 100,
                genres = listOf(MovieGenres(name = text), MovieGenres(name = text)),
                vote_average = 8.5,
                tagline = text,
                overview = stringResource(id = R.string.placeholder_description)
            )
        ) {}
    }
}
