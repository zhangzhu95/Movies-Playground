package com.zhangzhu95.domain.movies

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies1
import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies2
import com.zhangzhu95.data.fakes.FakeMovies.fakeMovies3
import com.zhangzhu95.data.fakes.FakeMoviesListResponse
import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.movies.models.MoviesListResponse
import com.zhangzhu95.domain.movies.models.HomeSections
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FetchHomeSectionsUseCaseTest {

    private val moviesRepository: MoviesRepository = mockk()
    private val sut = FetchHomeSectionsUseCase(moviesRepository)

    @Before
    fun setup() {
        // By default, consider calls as successful
        coEvery { moviesRepository.fetchTopRated() } returns Response.Success(
            FakeMoviesListResponse.regular1
        )

        coEvery { moviesRepository.fetchUpcoming() } returns Response.Success(
            FakeMoviesListResponse.regular2
        )

        coEvery { moviesRepository.fetchTrendingAll() } returns Response.Success(
            FakeMoviesListResponse.regular3
        )
    }

    @Test
    fun `test success when successful data fetch`() = runBlocking {
        val result = sut.invoke()
        val expected = Response.Success(
            HomeSections(
                trending = fakeMovies3,
                topRated = fakeMovies1,
                upcoming = fakeMovies2
            )
        )
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

    @Test
    fun `test success when successful data fetch except topRated`() = runBlocking {
        coEvery {
            moviesRepository.fetchTopRated()
        } returns Response.Error("Error")

        val result = sut.invoke()
        val expected = Response.Success(
            HomeSections(
                trending = fakeMovies3,
                topRated = emptyList(),
                upcoming = fakeMovies2
            )
        )
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

    @Test
    fun `test success when successful data fetch except upcoming`() = runBlocking {
        coEvery {
            moviesRepository.fetchUpcoming()
        } returns Response.Error("Error")

        val result = sut.invoke()
        val expected = Response.Success(
            HomeSections(
                trending = fakeMovies3,
                topRated = fakeMovies1,
                upcoming = emptyList()
            )
        )
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

    @Test
    fun `test success when successful data fetch except trending`() = runBlocking {
        coEvery {
            moviesRepository.fetchTrendingAll()
        } returns Response.Error("Error")

        val result = sut.invoke()
        val expected = Response.Success(
            HomeSections(
                trending = emptyList(),
                topRated = fakeMovies1,
                upcoming = fakeMovies2
            )
        )
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

    @Test
    fun `test error when failed data fetch`() = runBlocking {
        coEvery {
            moviesRepository.fetchTrendingAll()
        } returns Response.Error("Error")

        coEvery {
            moviesRepository.fetchUpcoming()
        } returns Response.Error("Error")

        coEvery {
            moviesRepository.fetchTopRated()
        } returns Response.Error("Error")

        val result = sut.invoke()
        val expected = Response.Error<MoviesListResponse>(message = "Empty")
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

}