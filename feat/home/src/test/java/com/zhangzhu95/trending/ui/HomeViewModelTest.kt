package com.zhangzhu95.trending.ui

import com.zhangzhu95.core.networking.Response
import com.zhangzhu95.data.fakes.fakeMovies
import com.zhangzhu95.data.fakes.fakeMovies2
import com.zhangzhu95.data.fakes.fakeMovies3
import com.zhangzhu95.domain.movies.FetchHomeSectionsUseCase
import com.zhangzhu95.domain.movies.models.HomeSections
import com.zhangzhu95.testing.MainDispatcherRule
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class HomeViewModelTest {

    private val fetchHomeSectionsUseCase: FetchHomeSectionsUseCase = mockk()
    private lateinit var sut: HomeViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `test successful data fetch after init`() = runBlocking {
        every {
            fetchHomeSectionsUseCase.invoke()
        } returns flow {
            emit(
                Response.Success(
                    data = HomeSections(
                        trending = fakeMovies,
                        upcoming = fakeMovies2,
                        topRated = fakeMovies3
                    )
                )
            )
        }

        sut = HomeViewModel(fetchHomeSectionsUseCase)

        val expected = HomeViewState.Sections(
            HomeSections(
                trending = fakeMovies,
                upcoming = fakeMovies2,
                topRated = fakeMovies3
            )
        )

        assertEquals(expected, sut.viewState.value)
    }

    @Test
    fun `test unsuccessful data fetch after init`() = runBlocking {
        every {
            fetchHomeSectionsUseCase.invoke()
        } returns flow {
            emit(
                Response.Error("Error message")
            )
        }

        sut = HomeViewModel(fetchHomeSectionsUseCase)

        val expected = HomeViewState.Error("Error message")

        assertEquals(expected, sut.viewState.value)
    }

    @Test
    fun `test loading state after init`() = runBlocking {
        every {
            fetchHomeSectionsUseCase.invoke()
        } returns flow {
            emit(Response.Loading)
        }

        sut = HomeViewModel(fetchHomeSectionsUseCase)

        val expected = HomeViewState.Loading

        assertEquals(expected, sut.viewState.value)
    }
}
