package com.zhangzhu95.details.ui

import androidx.lifecycle.SavedStateHandle
import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.movies.models.MovieHistory
import com.zhangzhu95.domain.movies.FetchMovieDetailsUseCase
import com.zhangzhu95.domain.movies.SaveMovieInHistoryUseCase
import com.zhangzhu95.domain.movies.models.MovieFullDetails
import com.zhangzhu95.testing.MainDispatcherRule
import com.zhangzhu95.testing.fake.FakeActor
import com.zhangzhu95.testing.fake.FakeCastResponse
import com.zhangzhu95.testing.fake.FakeMovieDetails
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    private val fetchMovieDetailsUseCase: FetchMovieDetailsUseCase = mockk(relaxed = true)
    private val saveMovieInHistoryUseCase: SaveMovieInHistoryUseCase = mockk(relaxed = true)
    private val savedStateHandle: SavedStateHandle = mockk(relaxed = true)
    private lateinit var sut: DetailsViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val movieId = "10"

    private fun initSUT() {
        sut = DetailsViewModel(
            fetchMovieDetailsUseCase,
            saveMovieInHistoryUseCase,
            savedStateHandle
        )
    }

    @Before
    fun setup() {
        every { savedStateHandle.get<String>("movieId") } returns movieId
    }

    @Test
    fun `test successful loadDetails`() = runBlocking {
        every {
            fetchMovieDetailsUseCase.invoke(any())
        } returns flow {
            emit(
                Response.Success(
                    MovieFullDetails(
                        cast = FakeCastResponse.regular,
                        details = FakeMovieDetails.regularDetails
                    )
                )
            )
        }
        initSUT()
        coVerify(exactly = 1) {
            fetchMovieDetailsUseCase.invoke(movieId)
            with(FakeMovieDetails.regularDetails) {
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
    fun `test failed fetching movie details`() = runBlocking {
        every {
            fetchMovieDetailsUseCase.invoke(any())
        } returns flow {
            emit(
                Response.Error("ErrorDetails")
            )
        }
        initSUT()
        coVerify {
            fetchMovieDetailsUseCase.invoke(movieId)
        }

        coVerify(exactly = 0) {
            saveMovieInHistoryUseCase.invoke(any())
        }

        val expected = DetailsViewState.Error("ErrorDetails")
        assertEquals(expected, sut.viewState.value)
    }

    @Test
    fun `test loading state`() = runBlocking {
        every {
            fetchMovieDetailsUseCase.invoke(any())
        } returns flow {
            emit(Response.Loading)
        }
        initSUT()
        coVerify {
            fetchMovieDetailsUseCase.invoke(movieId)
        }

        val expected = DetailsViewState.Loading
        assertEquals(expected, sut.viewState.value)
    }
}