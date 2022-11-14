package com.zhangzhu95.domain.search

import com.zhangzhu95.data.search.SearchRepository
import javax.inject.Inject

class GetSearchQueriesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke(): List<String> {
        return repository.getQueries()
    }
}
