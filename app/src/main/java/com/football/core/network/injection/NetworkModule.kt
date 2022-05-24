package com.football.core.network.injection

import com.football.R
import com.football.core.network.ApiHelper
import com.football.core.network.ApiHelperImpl
import com.football.core.network.api.ApiInterceptor
import com.football.core.network.api.NewsApi
import com.football.core.network.utils.LoggerHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = module {
    single<NewsApi> {
        Retrofit.Builder()
            .baseUrl(androidContext().getString(R.string.base_url))
            .addConverterFactory(MoshiConverterFactory.create())
            .client(
                LoggerHttpClient
                    .getOkHttpClient()
                    .addInterceptor(get<ApiInterceptor>())
                    .build()
            )
            .build()
            .create(NewsApi::class.java)
    }
    single {
        ApiInterceptor(
            newsResponseString = androidContext().assets.open("news.json")
                .bufferedReader().use { it.readText() }
        )
    }
    single<ApiHelper> {
        ApiHelperImpl(newsApi = get())
    }
}