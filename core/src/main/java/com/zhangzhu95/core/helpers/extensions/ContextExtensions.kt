package com.zhangzhu95.core.helpers.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import timber.log.Timber

fun Context.openWebPage(url: String){
    try {
        val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        startActivity(webIntent)
    } catch (e: ActivityNotFoundException) {
        Timber.e("Couldn't open the browser")
    }
}