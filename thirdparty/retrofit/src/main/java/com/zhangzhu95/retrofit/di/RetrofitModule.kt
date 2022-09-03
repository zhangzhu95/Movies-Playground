package com.zhangzhu95.retrofit.di

import com.zhangzhu95.core.config.Config
import com.zhangzhu95.retrofit.interceptors.AuthInterceptor
import com.zhangzhu95.retrofit.services.ActorsService
import com.zhangzhu95.retrofit.services.MoviesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RetrofitModule {

    @Provides
    fun provideAuthInterceptor(): AuthInterceptor {
        return AuthInterceptor()
    }

    @Provides
    fun provideOkHttpClient(
        authInterceptor: AuthInterceptor
    ): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(Config.CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(Config.READ_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(Config.WRITE_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(authInterceptor)
            .build()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(): GsonConverterFactory = GsonConverterFactory.create()

    private fun createRetrofit(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Config.API_ENDPOINT)
            .client(okhttpClient)
            .addConverterFactory(converterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideMoviesService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): MoviesService {
        return provideService(okhttpClient, converterFactory, MoviesService::class.java)
    }

    @Singleton
    @Provides
    fun provideActorsService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory
    ): ActorsService {
        return provideService(okhttpClient, converterFactory, ActorsService::class.java)
    }

    private fun <T> provideService(
        okhttpClient: OkHttpClient,
        converterFactory: GsonConverterFactory,
        clazz: Class<T>
    ): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
