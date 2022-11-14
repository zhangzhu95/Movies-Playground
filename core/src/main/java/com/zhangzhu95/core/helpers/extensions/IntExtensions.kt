package com.zhangzhu95.core.helpers.extensions

import com.zhangzhu95.core.config.Config

fun Int.toDuration(): String {
    val hours = this / Config.Date.MINUTES_IN_HOUR
    val minutes = this - (Config.Date.MINUTES_IN_HOUR * hours)

    return if (hours == 0)
        "${minutes}m"
    else
        "${hours}h${minutes}m"
}
