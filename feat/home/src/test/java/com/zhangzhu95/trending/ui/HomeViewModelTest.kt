package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.fakes.fakeMovies
import com.zhangzhu95.data.movies.models.MoviesListResponse
import com.zhangzhu95.domain.movies.FetchTopRatedMoviesUseCase
import com.zhangzhu95.domain.movies.FetchTrendingUseCase
import com.zhangzhu95.domain.movies.FetchUpcomingMoviesUseCase
import com.zhangzhu95.testing.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val fetchTrendingViewState: FetchTrendingUseCase = mockk()
    private val fetchTopRatedMoviesUseCase: FetchTopRatedMoviesUseCase = mockk()
    private val fetchUpcomingMoviesUseCase: FetchUpcomingMoviesUseCase = mockk()
    private lateinit var sut: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        mockSuccessfulFetchTopRatedMovies()
        mockSuccessfulTrendingMovies()
        mockSuccessfulUpcomingMovies()

        sut = HomeViewModel(
            fetchTrendingViewState,
            fetchTopRatedMoviesUseCase,
            fetchUpcomingMoviesUseCase
        )
    }

    @Test
    fun `test data loaded successfully after init`() = runTest {
        val expected = HomeViewState.Success(
            list = listOf(
                HomeSections.HorizontalMoviesSection.TrendingMoviesSection(fakeMovies),
                HomeSections.HorizontalMoviesSection.TopRatedMoviesSection(fakeMovies),
                HomeSections.HorizontalMoviesSection.UpcomingMoviesSection(fakeMovies),
            )
        )

        assertEquals(expected, sut.viewState.value)
    }

    private fun mockSuccessfulFetchTopRatedMovies() {
        coEvery {
            fetchTopRatedMoviesUseCase.invoke()
        }.returns(
            Response.Success(data = MoviesListResponse(page = 1, results = fakeMovies))
        )
    }

    private fun mockSuccessfulTrendingMovies() {
        coEvery {
            fetchTrendingViewState.invoke()
        }.returns(
            Response.Success(data = MoviesListResponse(page = 1, results = fakeMovies))
        )
    }

    private fun mockSuccessfulUpcomingMovies() {
        coEvery {
            fetchUpcomingMoviesUseCase.invoke()
        }.returns(
            Response.Success(data = MoviesListResponse(page = 1, results = fakeMovies))
        )
    }
}
