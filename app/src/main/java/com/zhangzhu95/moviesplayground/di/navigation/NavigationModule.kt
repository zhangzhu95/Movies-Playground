package com.zhangzhu95.moviesplayground.di.navigation

import com.zhangzhu95.trending.ui.TrendingNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @Provides
    fun provideTrendingNavigation(): TrendingNavigation = TrendingNavigationImpl

}
