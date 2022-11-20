package com.zhangzhu95.domain.movies

import com.zhangzhu95.data.fakes.FakeMovies.fakeMoviesHistory
import com.zhangzhu95.data.fakes.FakeQueries.fakeQueriesList
import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.search.SearchRepository
import com.zhangzhu95.domain.movies.models.PreviousSearch
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetPreviousSearchUseCaseTest {

    private val moviesRepository: MoviesRepository = mockk()
    private val searchRepository: SearchRepository = mockk()
    private val sut = GetPreviousSearchUseCase(moviesRepository, searchRepository)

    @Test
    fun `test success when data loaded`() = runBlocking {
        coEvery {
            moviesRepository.getMoviesHistory()
        } returns fakeMoviesHistory

        coEvery {
            searchRepository.getQueries()
        } returns fakeQueriesList

        val result = sut.invoke()
        val expected = PreviousSearch(history = fakeMoviesHistory, keywords = fakeQueriesList)

        assertEquals(expected, result.first())
    }

    @Test
    fun `test success when no keywords`() = runBlocking {
        coEvery {
            moviesRepository.getMoviesHistory()
        } returns fakeMoviesHistory

        coEvery {
            searchRepository.getQueries()
        } returns emptyList()

        val result = sut.invoke()
        val expected = PreviousSearch(history = fakeMoviesHistory, keywords = emptyList())

        assertEquals(expected, result.first())
    }

    @Test
    fun `test success when no movies in history`() = runBlocking {
        coEvery {
            moviesRepository.getMoviesHistory()
        } returns emptyList()

        coEvery {
            searchRepository.getQueries()
        } returns fakeQueriesList

        val result = sut.invoke()
        val expected = PreviousSearch(history = emptyList(), keywords = fakeQueriesList)

        assertEquals(expected, result.first())
    }

    @Test
    fun `test success when no movies and no keywords in history`() = runBlocking {
        coEvery {
            moviesRepository.getMoviesHistory()
        } returns emptyList()

        coEvery {
            searchRepository.getQueries()
        } returns emptyList()

        val result = sut.invoke()
        val expected = PreviousSearch(history = emptyList(), keywords = emptyList())

        assertEquals(expected, result.first())
    }
}