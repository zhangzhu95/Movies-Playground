package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.CastResponse
import com.zhangzhu95.data.movies.models.MovieDetails
import com.zhangzhu95.data.movies.models.MovieHistory
import com.zhangzhu95.domain.actors.FetchMovieActorsUseCase
import com.zhangzhu95.domain.movies.FetchDetailsUseCase
import com.zhangzhu95.domain.movies.SaveMovieInHistoryUseCase
import com.zhangzhu95.testing.MainDispatcherRule
import com.zhangzhu95.testing.fake.FakeActor
import com.zhangzhu95.testing.fake.FakeCastResponse
import com.zhangzhu95.testing.fake.FakeMovieDetails
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    private val fetchDetailsUseCase: FetchDetailsUseCase = mockk(relaxed = true)
    private val fetchMovieActorsUseCase: FetchMovieActorsUseCase = mockk(relaxed = true)
    private val saveMovieInHistoryUseCase: SaveMovieInHistoryUseCase = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private lateinit var sut: DetailsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val movieId = "10"

    private fun initSUT() {
        sut = DetailsViewModel(
            fetchDetailsUseCase,
            fetchMovieActorsUseCase,
            saveMovieInHistoryUseCase,
            savedStateHandle
        )
    }

    @Before
    fun setup() {
        mockSuccessfulFetchDetails()
        mockSuccessfulFetchMovieActors()
        mockSavedStateHandle()
    }

    @Test
    fun `test successful loadDetails`() = runTest {
        initSUT()
        assertEquals(movieId, sut.movieId)
        coVerify(exactly = 1) {
            fetchDetailsUseCase.invoke(movieId)
            fetchMovieActorsUseCase.invoke(movieId)
            FakeMovieDetails.regularDetails.apply {
                saveMovieInHistoryUseCase.invoke(
                    MovieHistory(
                        id = id,
                        name = originalTitle,
                        poster = posterPath
                    )
                )
            }
        }

        val expected = DetailsViewState.Success(
            FakeMovieDetails.regularDetails,
            FakeActor.listActors
        )
        assertEquals(expected, sut.viewState.value)
    }

    @Test
    fun `test missing id`() = runTest {
        every { savedStateHandle.get<String>("movieId") } returns null
        initSUT()
        coVerify(exactly = 0) {
            fetchDetailsUseCase.invoke(any())
            fetchMovieActorsUseCase.invoke(any())
            saveMovieInHistoryUseCase.invoke(any())
        }
        assertEquals(DetailsViewState.Idle, sut.viewState.value)
    }

    @Test
    fun `test failed fetching movie details`() = runTest {
        mockFailedFetchDetails()
        initSUT()
        assertEquals(movieId, sut.movieId)
        coVerify(exactly = 1) {
            fetchDetailsUseCase.invoke(movieId)
        }

        coVerify(exactly = 0) {
            saveMovieInHistoryUseCase.invoke(any())
        }

        val expected = DetailsViewState.Error("ErrorDetails")
        assertEquals(expected, sut.viewState.value)
    }

    @Test
    fun `test failed fetching cast`() = runTest {
        mockFailedFetchMovieActors()
        initSUT()
        assertEquals(movieId, sut.movieId)
        coVerify(exactly = 1) {
            fetchDetailsUseCase.invoke(movieId)
            fetchMovieActorsUseCase.invoke(movieId)
            FakeMovieDetails.regularDetails.apply {
                saveMovieInHistoryUseCase.invoke(
                    MovieHistory(
                        id = id,
                        name = originalTitle,
                        poster = posterPath
                    )
                )
            }
        }

        val expected = DetailsViewState.Success(FakeMovieDetails.regularDetails, emptyList())
        assertEquals(expected, sut.viewState.value)
    }

    private fun mockSavedStateHandle() {
        every { savedStateHandle.get<String>("movieId") } returns movieId
    }


    private fun mockSuccessfulFetchDetails() {
        val response = Response.Success(data = FakeMovieDetails.regularDetails)
        coEvery { fetchDetailsUseCase.invoke(any()) } returns response
    }

    private fun mockFailedFetchDetails() {
        val response = Response.Error<MovieDetails>(message = "ErrorDetails")
        coEvery { fetchDetailsUseCase.invoke(any()) } returns response
    }

    private fun mockSuccessfulFetchMovieActors() {
        val response = Response.Success(data = FakeCastResponse.regular)
        coEvery { fetchMovieActorsUseCase.invoke(any()) } returns response
    }

    private fun mockFailedFetchMovieActors() {
        val response = Response.Error<CastResponse>(message = "ErrorActors")
        coEvery { fetchMovieActorsUseCase.invoke(any()) } returns response
    }
}