package com.zhangzhu95.room.data

import com.zhangzhu95.data.search.SearchLocalSource
import com.zhangzhu95.room.dao.SearchQueryDao
import com.zhangzhu95.room.entities.SearchQuery
import javax.inject.Inject

class SearchLocalSourceImpl @Inject constructor(private val searchQueryDao: SearchQueryDao) :
    SearchLocalSource {

    override suspend fun saveQuery(query: String) {
        searchQueryDao.insertAll(SearchQuery(query))
    }

    override suspend fun getQueries(): List<String> {
        val list = searchQueryDao.getSearchQueries()
        return list.map { it.query }
    }
}
