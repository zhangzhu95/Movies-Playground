package com.zhangzhu95.testing.fake

import com.zhangzhu95.data.movies.models.Actor

object FakeActor {
    val listActors = (1..3).map {
        Actor(
            id = it,
            name = "Name $it",
            profilePath = "image$it.png",
            character = "character $it"
        )
    }
}