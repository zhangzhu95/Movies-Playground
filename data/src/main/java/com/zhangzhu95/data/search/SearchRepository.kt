package com.zhangzhu95.data.search

import javax.inject.Inject

class SearchRepository @Inject constructor(private val searchLocalSource: SearchLocalSource) {

    suspend fun saveQuery(query: String) {
        searchLocalSource.saveQuery(query)
    }

    suspend fun getQueries(): List<String> {
        return searchLocalSource.getQueries()
    }
}
