package com.zhangzhu95.moviesplayground.di.navigation

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import com.zhangzhu95.details.ui.DetailsNavigation
import timber.log.Timber

object DetailsNavigationImpl : DetailsNavigation {
    override fun discoverMovie(context: Context, url: String) {
        try {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            context.startActivity(webIntent)
        } catch (e: ActivityNotFoundException) {
            Timber.e("Couldn't open the browser")
        }
    }
}
