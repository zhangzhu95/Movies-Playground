package com.zhangzhu95.trending.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zhangzhu95.compose.themes.AppTheme
import com.zhangzhu95.compose.widgets.LoadingView
import com.zhangzhu95.compose.widgets.MovieItem
import com.zhangzhu95.compose.widgets.SearchBar
import com.zhangzhu95.compose.widgets.Spacing
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies1
import com.zhangzhu95.data.movies.models.Movie
import com.zhangzhu95.domain.movies.models.HomeSections
import com.zhangzhu95.trending.R
import com.zhangzhu95.compose.R as RC

@Composable
fun HomeNavScreen(
    onMovieSelected: (Int) -> Unit,
    onSearchClick: () -> Unit
) {
    val viewModel = hiltViewModel<HomeViewModel>()
    val viewState by viewModel.viewState.collectAsState(HomeViewState.Idle)

    HomeScreen(
        viewState,
        onMovieSelected = onMovieSelected,
        onSearchClick = onSearchClick
    )
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
            is HomeViewState.Sections -> {

                LazyColumn {
                    item {
                        HomeSectionList(
                            title = R.string.top_rated,
                            movies = viewState.sections.topRated,
                            onMovieSelected = onMovieSelected
                        )
                        Spacing.Vertical.Medium()
                    }

                    item {
                        HomeSectionList(
                            title = R.string.upcoming,
                            movies = viewState.sections.upcoming,
                            onMovieSelected = onMovieSelected
                        )
                        Spacing.Vertical.Medium()
                    }

                    item {
                        HomeSectionList(
                            title = R.string.trending,
                            movies = viewState.sections.trending,
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
internal fun HomeSectionList(
    @StringRes title: Int,
    movies: List<Movie>,
    onMovieSelected: (Int) -> Unit
) {

    Column {

        Text(
            modifier = Modifier.padding(horizontal = 24.dp),
            text = stringResource(id = title),
            style = MaterialTheme.typography.h3
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

    val currentState = HomeViewState.Sections(
        sections = HomeSections(
            trending = fakeMovies1,
            upcoming = fakeMovies1,
            topRated = fakeMovies1
        )
    )

    AppTheme {
        Surface {
            HomeScreen(currentState, {}, {})
        }
    }
}
