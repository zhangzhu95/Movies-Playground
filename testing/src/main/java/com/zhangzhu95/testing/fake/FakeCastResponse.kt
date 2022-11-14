package com.zhangzhu95.testing.fake

import com.zhangzhu95.data.movies.models.CastResponse

object FakeCastResponse {
    val regular = CastResponse(
        id = 1,
        cast = FakeActor.listActors
    )
}