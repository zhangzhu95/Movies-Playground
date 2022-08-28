package com.zhangzhu95.core.helpers.extensions

import com.zhangzhu95.core.config.Config

fun String.toSmallPosterURL(): String {
    return Config.IMAGES_SMALL_PREFIX + this
}

fun String.toMediumPosterURL(): String {
    return Config.IMAGES_MEDIUM_PREFIX + this
}

fun String.toBigPosterURL(): String {
    return Config.IMAGES_BIG_PREFIX + this
}
