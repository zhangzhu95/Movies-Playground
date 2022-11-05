package com.zhangzhu95.domain.search

import com.zhangzhu95.data.search.SearchRepository
import javax.inject.Inject

class SaveSearchQueryUseCase @Inject constructor(
    private val searchRepository: SearchRepository
) {
    suspend operator fun invoke(query: String) {
        searchRepository.saveQuery(query)
    }
}
