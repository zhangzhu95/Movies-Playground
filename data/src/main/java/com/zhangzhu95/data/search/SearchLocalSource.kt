package com.zhangzhu95.data.search

interface SearchLocalSource {

    suspend fun saveQuery(query: String)

    suspend fun getQueries(): List<String>
}
