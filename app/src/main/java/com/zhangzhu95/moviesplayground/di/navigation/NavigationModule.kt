package com.zhangzhu95.moviesplayground.di.navigation

import com.zhangzhu95.search.SearchNavigation
import com.zhangzhu95.details.ui.DetailsNavigation
import com.zhangzhu95.trending.ui.HomeNavigation
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
class NavigationModule {

    @Provides
    fun provideTrendingNavigation(): HomeNavigation = HomeNavigationImpl

    @Provides
    fun provideDetailsNavigation(): DetailsNavigation = DetailsNavigationImpl

    @Provides
    fun provideSearchNavigation(): SearchNavigation = SearchNavigationImpl
}
