package com.zhangzhu95.domain.movies

import com.zhangzhu95.data.movies.MoviesRepository
import com.zhangzhu95.data.movies.models.MovieHistory
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

class SaveMovieInHistoryUseCaseTest {

    private val moviesRepository: MoviesRepository = mockk(relaxed = true)
    private val sut = SaveMovieInHistoryUseCase(moviesRepository)

    @Test
    fun `test invoke addToHistory`() = runBlocking {
        val movie = MovieHistory(10, "Poster", name = "Name")
        sut.invoke(movie)
        coVerify(exactly = 1) {
            moviesRepository.addToHistory(movie)
        }
    }

}