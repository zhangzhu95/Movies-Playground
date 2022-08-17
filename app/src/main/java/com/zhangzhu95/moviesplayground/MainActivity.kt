package com.zhangzhu95.moviesplayground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.zhangzhu95.trending.ui.TrendingFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        TrendingFragmentDirections
    }
}
