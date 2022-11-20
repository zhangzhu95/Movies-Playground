package com.zhangzhu95.domain.movies

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.actors.ActorsRepository
import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.domain.movies.models.MovieFullDetails
import com.zhangzhu95.data.fakes.FakeCastResponse
import com.zhangzhu95.data.fakes.FakeMovieDetails
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class FetchMovieDetailsUseCaseTest {

    private val moviesRepository: MoviesRepository = mockk()
    private val actorsRepository: ActorsRepository = mockk()
    private val sut = FetchMovieDetailsUseCase(moviesRepository, actorsRepository)
    private val movieId = "100"

    @Test
    fun `test error when movie id is null or empty`() = runBlocking {
        val resultNull = sut.invoke(id = null).first()
        val resultEmpty = sut.invoke(id = "").first()
        val expected = Response.Error<MovieFullDetails>("Error")
        assertEquals(expected, resultNull)
        assertEquals(expected, resultEmpty)
    }

    @Test
    fun `test success when movie details and actors are fetched `() = runBlocking {
        coEvery {
            moviesRepository.fetchDetails(any())
        } returns Response.Success(data = FakeMovieDetails.regularDetails)

        coEvery {
            actorsRepository.getCredits(any())
        } returns Response.Success(data = FakeCastResponse.regular)

        val result = sut.invoke(movieId)
        val expected = Response.Success(
            MovieFullDetails(
                details = FakeMovieDetails.regularDetails,
                cast = FakeCastResponse.regular
            )
        )
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

    @Test
    fun `test success when successful movie details fetch but failed actors fetch`() = runBlocking {
        coEvery {
            moviesRepository.fetchDetails(any())
        } returns Response.Success(data = FakeMovieDetails.regularDetails)

        coEvery {
            actorsRepository.getCredits(any())
        } returns Response.Error("Error")

        val result = sut.invoke(movieId)
        val expected = Response.Success(
            MovieFullDetails(
                details = FakeMovieDetails.regularDetails,
                cast = null
            )
        )
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }

    @Test
    fun `test error when failed movie details and actors fetch`() = runBlocking {
        coEvery {
            moviesRepository.fetchDetails(any())
        } returns Response.Error("Error")

        coEvery {
            actorsRepository.getCredits(any())
        } returns Response.Error("Error")

        val result = sut.invoke(movieId)
        val expected = Response.Error<MovieFullDetails>("Failed to load the screen")
        assertEquals(Response.Loading, result.first())
        assertEquals(expected, result.last())
    }
}