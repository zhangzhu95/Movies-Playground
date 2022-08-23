package com.zhangzhu95.core.helpers.extensions

import com.zhangzhu95.core.config.Config

fun String.toPosterURL(): String {
    return Config.IMAGES_PREFIX + this
}
