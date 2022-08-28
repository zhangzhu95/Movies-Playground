package com.zhangzhu95.trending.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.core.ui.widgets.LoadingView
import com.zhangzhu95.core.ui.widgets.MovieItem
import com.zhangzhu95.core.ui.widgets.Spacing
import com.zhangzhu95.core.ui.widgets.styles.AppTheme
import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.trending.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    @Inject
    lateinit var navigation: HomeNavigation

    private val viewModel: HomeViewModel by viewModels()

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
                    val topRatedState by viewModel.topRatedViewState.collectAsState(initial = HomeViewState.Loading)
                    val trendingState by viewModel.trendingViewState.collectAsState(initial = HomeViewState.Loading)
                    HomeScreen(trendingState, topRatedState) {
                        navigation.goToMovieDetails(findNavController(), movieId = it.toString())
                    }
                }
            }
        }
    }
}

@Composable
internal fun HomeScreen(
    trendingState: HomeViewState,
    topRatedState: HomeViewState,
    onSelectMovie: (Int) -> Unit
) {
    Column {
        Spacing.Vertical.Bigger()
        RenderHomeSection(
            title = R.string.trending,
            state = trendingState,
            onSelectMovie = onSelectMovie
        )
        Spacing.Vertical.Medium()
        RenderHomeSection(
            title = R.string.top_rated,
            state = topRatedState,
            onSelectMovie = onSelectMovie
        )
    }
}

@Composable
internal fun RenderHomeSection(
    @StringRes title: Int,
    state: HomeViewState,
    onSelectMovie: (Int) -> Unit
) {
    when (state) {
        is HomeViewState.Success -> HomeSectionList(title = title, state = state, onSelectMovie)
        is HomeViewState.Loading -> LoadingView()
        else -> Text(text = "Unknown state")
    }
}

@Composable
internal fun HomeSectionList(
    @StringRes title: Int,
    state: HomeViewState.Success,
    onSelectMovie: (Int) -> Unit
) {

    Column {

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = stringResource(id = title),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        LazyRow(contentPadding = PaddingValues(horizontal = 15.dp)) {
            items(count = state.list.size, key = { state.list[it].id }, itemContent = { index ->
                val movie = state.list[index]
                MovieItem(
                    movie.id,
                    movie.posterPath.toSmallPosterURL(),
                    onSelectMovie
                )
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {

    val currentState = HomeViewState.Success(
        listOf(
            Movie(title = "Movie 1"),
            Movie(title = "Movie 2"),
            Movie(title = "Movie 3"),
        )
    )

    AppTheme {
        Surface {
            HomeScreen(currentState, currentState) {}
        }
    }
}
