package com.zhangzhu95.core.helpers.extensions

fun Double.round(decimals: Int = 2): String = "%.${decimals}f".format(this)
