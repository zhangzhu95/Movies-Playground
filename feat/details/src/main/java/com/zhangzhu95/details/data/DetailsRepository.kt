package com.zhangzhu95.details.data

import com.zhangzhu95.core.data.networking.details.DetailsRemoteSource
import javax.inject.Inject

class DetailsRepository @Inject constructor(private val remoteDataSource: DetailsRemoteSource) {
    suspend fun fetchDetails(id: String) = remoteDataSource.getDetails(id)
}
