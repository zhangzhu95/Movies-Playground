package com.zhangzhu95.trending.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
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
import com.zhangzhu95.compose.themes.AppTheme
import com.zhangzhu95.compose.widgets.LoadingView
import com.zhangzhu95.compose.widgets.MovieItem
import com.zhangzhu95.compose.widgets.SearchBar
import com.zhangzhu95.compose.widgets.Spacing
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.data.fakes.fakeMovies
import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.trending.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.filter
import javax.inject.Inject
import com.zhangzhu95.compose.R as RC

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
                    val viewState by viewModel.viewState.filter {
                        it is HomeViewState.Success
                    }.collectAsState(HomeViewState.Idle)

                    HomeScreen(
                        viewState,
                        onMovieSelected = {
                            navigation.goToMovieDetails(
                                findNavController(),
                                movieId = it.toString()
                            )
                        }, onSearchClick = { navigation.goToSearch(findNavController()) }
                    )
                }
            }
        }
    }
}

@Composable
internal fun HomeScreen(
    viewState: HomeViewState,
    onMovieSelected: (Int) -> Unit,
    onSearchClick: () -> Unit,
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Spacing.Vertical.Tiny()

        SearchBar(
            hint = RC.string.search_movie_hint,
            onTouch = onSearchClick
        )

        when (viewState) {
            is HomeViewState.Loading -> LoadingView()
            is HomeViewState.Success -> {
                LazyColumn(contentPadding = PaddingValues(vertical = 10.dp)) {
                    items(count = viewState.list.size, key = { viewState.list[it].sectionTitle }) {
                        RenderHomeSection(
                            title = viewState.list[it].sectionTitle,
                            section = viewState.list[it],
                            onMovieSelected = onMovieSelected
                        )
                        Spacing.Vertical.Medium()
                    }
                }
            }
            else -> {}
        }
    }
}

@Composable
internal fun RenderHomeSection(
    @StringRes title: Int,
    section: HomeSections,
    onMovieSelected: (Int) -> Unit
) {
    when (section) {
        is HomeSections.HorizontalMoviesSection -> HomeSectionList(
            title = title,
            movies = section.list,
            onMovieSelected
        )
    }
}

@Composable
internal fun HomeSectionList(
    @StringRes title: Int,
    movies: List<Movie>,
    onMovieSelected: (Int) -> Unit
) {

    Column {

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = stringResource(id = title),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            items(count = movies.size, key = { movies[it].id }, itemContent = { index ->
                val movie = movies[index]
                MovieItem(
                    id = movie.id,
                    posterUrl = movie.posterPath.toSmallPosterURL(),
                    onMovieClicked = onMovieSelected
                )
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomePreview() {

    val currentState = HomeViewState.Success(
        listOf(
            HomeSections.HorizontalMoviesSection(title = R.string.trending, list = fakeMovies),
            HomeSections.HorizontalMoviesSection(title = R.string.top_rated, list = fakeMovies),
        )
    )

    AppTheme {
        Surface {
            HomeScreen(currentState, {}, {})
        }
    }
}
