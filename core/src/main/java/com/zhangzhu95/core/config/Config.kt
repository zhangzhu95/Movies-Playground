package com.zhangzhu95.core.config

object Config {
    const val CONNECT_TIMEOUT = 20L
    const val READ_TIMEOUT = 30L
    const val WRITE_TIMEOUT = 30L
    const val API_ENDPOINT = "https://api.themoviedb.org/3/"
    const val IMAGES_SMALL_PREFIX = "https://image.tmdb.org/t/p/w500/"
    const val IMAGES_MEDIUM_PREFIX = "https://image.tmdb.org/t/p/w500/"
    const val IMAGES_BIG_PREFIX = "https://image.tmdb.org/t/p/w500/"
    const val API_ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJlNGU4OWYzMWJjYmY4ZmEwMz" +
        "E3YzU4NjdkMDI3NmY2MiIsInN1YiI6IjYyZWU1N2VlODEzY2I2MDA5MjM5ZDgxYiIsInNjb3Blcy" +
        "I6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.1RFIxqkr2Cs77o6OhOGIGtT73obSRUQWv3UdyC0qIgA"

    object Date{
        const val SECONDS_IN_MINUTE = 60
        const val MINUTES_IN_HOUR = 60
    }
}
