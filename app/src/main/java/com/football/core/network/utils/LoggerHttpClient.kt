package com.football.core.network.utils

import com.football.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

object LoggerHttpClient {
    fun getOkHttpClient(): OkHttpClient.Builder = OkHttpClient.Builder().also {
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            it.addInterceptor(interceptor)
        }
    }
}