package com.zhangzhu95.data.movies.models

import com.google.gson.annotations.SerializedName

data class Actor(
    val id: Int = 0,
    val name: String = "",
    @SerializedName("profile_path")
    val profilePath: String = "",
    val character: String = ""
)
