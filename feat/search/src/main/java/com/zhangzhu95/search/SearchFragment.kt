package com.zhangzhu95.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.zhangzhu95.compose.themes.AppTheme
import com.zhangzhu95.compose.widgets.Chip
import com.zhangzhu95.compose.widgets.LoadingView
import com.zhangzhu95.compose.widgets.MovieHistoryItem
import com.zhangzhu95.compose.widgets.OnBottomReached
import com.zhangzhu95.compose.widgets.ScreenInformation
import com.zhangzhu95.compose.widgets.SearchBar
import com.zhangzhu95.compose.widgets.Spacing
import com.zhangzhu95.compose.widgets.VerticalMovieItem
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.data.fakes.fakeMovies
import com.zhangzhu95.data.movies.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.zhangzhu95.compose.R as RC

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel: SearchViewModel by viewModels()

    @Inject
    lateinit var navigation: SearchNavigation

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    SearchScreen(
                        query = viewModel.query.collectAsState().value,
                        viewState = viewModel.viewState.collectAsState().value,
                        event = viewModel.events.collectAsState(initial = SearchViewEvent.Idle).value,
                        onValueChange = viewModel::onSearchQueryChanged,
                        onSearch = viewModel::onNewSearch,
                        onMovieClicked = viewModel::onMovieClicked,
                        onLoadMore = viewModel::onLoadMore,
                        onSearchQueryChanged = viewModel::onSearchQueryChanged,
                        onNewSearch = viewModel::onNewSearch
                    )
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.events.flowWithLifecycle(lifecycle, Lifecycle.State.STARTED).collect {
                when (it) {
                    is SearchViewEvent.NavigationMovieDetails -> navigation.goToMovieDetails(
                        findNavController(),
                        it.id.toString()
                    )
                    else -> {}
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    query: String = "",
    viewState: SearchViewState = SearchViewState.Idle,
    event: SearchViewEvent = SearchViewEvent.Idle,
    onValueChange: (String) -> Unit = {},
    onSearch: () -> Unit = {},
    onMovieClicked: (Int) -> Unit = {},
    onLoadMore: () -> Unit = {},
    onSearchQueryChanged: (String) -> Unit = {},
    onNewSearch: () -> Unit = {},
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacing.Vertical.Tiny()

        SearchBar(
            hint = RC.string.search_movie_hint,
            value = query,
            onValueChange = onValueChange,
            onSearch = onSearch,
            focused = true
        )

        when (event) {
            is SearchViewEvent.Loading -> LoadingView()
            is SearchViewEvent.Error -> Snackbar { Text(text = "Error") }
            else -> {}
        }

        when (viewState) {
            is SearchViewState.Empty -> Empty()
            is SearchViewState.Start -> Start()
            is SearchViewState.AutocompleteHistory -> {
                SearchHistorySection(viewState, onSearchQueryChanged, onNewSearch, onMovieClicked)
            }
            is SearchViewState.SearchList -> MoviesList(viewState.list, onMovieClicked, onLoadMore)
            else -> {}
        }
    }
}

@Composable
private fun Empty() {
    Spacing.Vertical.Medium()
    ScreenInformation(
        message = R.string.no_movies_found,
        icon = RC.drawable.ic_baseline_search_24
    )
}

@Composable
private fun Start() {
    Spacing.Vertical.Medium()
    ScreenInformation(
        message = R.string.start_movie_search,
        icon = R.drawable.ic_baseline_local_movies_24
    )
}

@Composable
private fun MoviesList(
    movies: List<Movie>,
    onMovieClicked: (Int) -> Unit = {},
    onLoadMore: () -> Unit = {}
) {
    val listState = rememberLazyListState()

    LazyColumn(state = listState) {
        items(count = movies.size, key = { movies[it].id }, itemContent = { index ->
            val movie = movies[index]
            VerticalMovieItem(
                title = movie.title,
                language = movie.originalLanguage,
                id = movie.id,
                postureUrl = movie.posterPath.toSmallPosterURL(),
                rating = movie.voteAverage,
                onMovieClicked = onMovieClicked
            )
        })
    }

    listState.OnBottomReached(onLoadMore)
}

@Composable
private fun SearchHistorySection(
    history: SearchViewState.AutocompleteHistory,
    onSearchQueryChanged: (String) -> Unit = {},
    onNewSearch: () -> Unit = {},
    onMovieClicked: (Int) -> Unit = {}
) {

    Text(
        text = stringResource(id = R.string.previous_searches),
        style = MaterialTheme.typography.h3
    )

    Spacing.Vertical.Tiny()

    LazyRow {
        items(count = history.queryHistory.size, key = { history.queryHistory[it] }) {
            val query = history.queryHistory[it]
            Chip(
                text = query,
                icon = RC.drawable.ic_baseline_search_24,
                modifier = Modifier
                    .padding(4.dp)
                    .clickable {
                        onSearchQueryChanged(query)
                        onNewSearch()
                    }
            )
        }
    }

    Spacing.Vertical.Medium()

    Text(
        text = stringResource(id = R.string.recently_visited),
        style = MaterialTheme.typography.h3
    )

    Spacing.Vertical.Tiny()

    LazyRow {
        items(
            count = history.recentlyViewed.size,
            key = { history.recentlyViewed[it].id },
            itemContent = { index ->
                val movie = history.recentlyViewed[index]
                MovieHistoryItem(
                    id = movie.id,
                    postureUrl = movie.poster.toSmallPosterURL(),
                    onMovieClicked = onMovieClicked
                )
            }
        )
    }
}

@Preview
@Composable
private fun SearchScreenRecentlyViewPreview() {
    AppTheme {
        SearchScreen(
            viewState = SearchViewState.SearchList(
                list = fakeMovies
            )
        )
    }
}
