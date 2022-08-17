package com.zhangzhu95.trending.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.zhangzhu95.core.R
import com.zhangzhu95.core.data.networking.trending.Movie
import com.zhangzhu95.core.helpers.extensions.toPosterURL
import com.zhangzhu95.core.ui.compose.AppTheme
import com.zhangzhu95.core.ui.widgets.LoadingView
import com.zhangzhu95.core.ui.widgets.RemoteImage
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TrendingFragment : Fragment() {

    @Inject
    lateinit var navigation: TrendingNavigation

    private val viewModel: TrendingViewModel by viewModels()

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
                    val state by viewModel.viewState.collectAsState(initial = TrendingViewState.Loading)
                    renderTrendingScreen(state) {
                        navigation.goToMovieDetails(findNavController(), movieId = it.id.toString())
                    }
                }
            }
        }
    }
}

@Composable
internal fun renderTrendingScreen(state: TrendingViewState, onSelectMovie: (Movie) -> Unit) {
    when (state) {
        is TrendingViewState.Success -> TrendingList(state = state, onSelectMovie)
        is TrendingViewState.Loading -> LoadingView()
        else -> Text(text = "Unknown state")
    }
}

@Composable
internal fun TrendingList(state: TrendingViewState.Success, onSelectMovie: (Movie) -> Unit) {
    LazyColumn {
        items(state.list) { movie ->
            MovieCard(movie, onSelectMovie)
        }
    }
}

@Composable
internal fun MovieCard(
    movie: Movie = Movie(
        title = "Avengers: End Game",
        vote_average = 9.3,
        original_language = "en"
    ),
    onSelectMovie: (Movie) -> Unit
) {
    Box(Modifier.padding(10.dp)) {
        TextButton(onClick = { onSelectMovie(movie) }) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(10.dp),
                elevation = 5.dp
            ) {
                Row(modifier = Modifier.padding(10.dp)) {
                    RemoteImage(
                        url = movie.poster_path.toPosterURL(),
                        R.mipmap.movie_poster,
                        modifier = Modifier
                            .wrapContentHeight()
                            .width(120.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )

                    Column(modifier = Modifier.padding(start = 10.dp)) {
                        Text(
                            movie.title,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                        )

                        Row(modifier = Modifier.padding(top = 20.dp)) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_baseline_star_24),
                                contentDescription = "Rating",
                                tint = Color(0xFFD29B05)
                            )

                            Text(
                                movie.vote_average.toString(),
                                color = Color(0xFFD29B05),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                        }

                        Text(
                            modifier = Modifier.padding(top = 10.dp),
                            text = "Language : ${movie.original_language.uppercase()}",
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    AppTheme {
        Surface(
            modifier = Modifier.padding(20.dp)
        ) {
            MovieCard {}
        }
    }
}
