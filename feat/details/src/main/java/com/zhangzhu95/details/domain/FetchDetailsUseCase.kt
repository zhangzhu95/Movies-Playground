package com.zhangzhu95.details.domain

import com.zhangzhu95.core.data.networking.Response
import com.zhangzhu95.details.data.DetailsRepository
import com.zhangzhu95.details.ui.DetailsViewState
import javax.inject.Inject

internal class FetchDetailsUseCase @Inject constructor(
    private val detailsRepository: DetailsRepository
) {

    suspend operator fun invoke(id: String?): DetailsViewState {
        if (id == null) {
            return DetailsViewState.InvalidMovieId
        }

        val result = detailsRepository.fetchDetails(id)
        return when (result) {
            is Response.Success -> DetailsViewState.Success(result.data!!)
            is Response.Error -> DetailsViewState.Error(result.message)
        }
    }
}
