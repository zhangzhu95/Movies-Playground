package com.zhangzhu.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.zhangzhu95.core.helpers.extensions.toSmallPosterURL
import com.zhangzhu95.core.ui.widgets.LoadingView
import com.zhangzhu95.core.ui.widgets.ScreenInformation
import com.zhangzhu95.core.ui.widgets.SearchBar
import com.zhangzhu95.core.ui.widgets.Spacing
import com.zhangzhu95.core.ui.widgets.VerticalMovieItem
import com.zhangzhu95.core.ui.widgets.styles.AppTheme
import com.zhangzhu95.data.movies.models.Movie
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import com.zhangzhu95.core.R as RC

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
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                AppTheme {
                    val query = viewModel.query.collectAsState()
                    val viewState = viewModel.viewState.collectAsState()
                    SearchScreen(
                        query.value,
                        viewState.value,
                        viewModel::onSearchQueryChanged,
                        viewModel::search
                    ) {
                        navigation.goToMovieDetails(findNavController(), it.toString())
                    }
                }
            }
        }
    }
}

@Composable
fun SearchScreen(
    query: String,
    viewState: SearchViewState,
    onQueryChanger: (String) -> Unit,
    onSearch: () -> Unit,
    onMovieClicked: (Int) -> Unit
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Spacing.Vertical.Tiny()

        SearchBar(
            hint = RC.string.search_movie_hint,
            value = query,
            onValueChange = onQueryChanger,
            onSearch = onSearch
        )

        when (viewState) {
            is SearchViewState.Empty -> Empty()
            is SearchViewState.Start -> Start()
            is SearchViewState.SearchList -> MoviesList(viewState.list, onMovieClicked)
            else -> {}
        }

        if (viewState is SearchViewState.Loading) {
            LoadingView()
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
private fun MoviesList(movies: List<Movie>, onMovieClicked: (Int) -> Unit) {
    LazyColumn {
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
}

@Preview
@Composable
private fun SearchScreenPreview() {
    AppTheme {
        SearchScreen("Hola", SearchViewState.Empty, {}, {}, {})
    }
}
