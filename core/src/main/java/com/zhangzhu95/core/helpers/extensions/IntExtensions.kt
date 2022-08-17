package com.zhangzhu95.core.helpers.extensions

fun Int.toDuration(): String {
    val hours = this / 60
    val minutes = this - (60 * hours)

    return if (hours == 0)
        "${minutes}m"
    else
        "${hours}h${minutes}m"
}
