package com.football.core.network.api

import com.football.core.network.dto.NewsResponse
import retrofit2.http.GET

interface NewsApi {
    @GET("news")
    suspend fun getNews(): NewsResponse
}