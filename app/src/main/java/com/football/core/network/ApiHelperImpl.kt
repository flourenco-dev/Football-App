package com.football.core.network

import com.football.core.network.api.NewsApi
import com.football.core.network.dto.NewsResponse

internal class ApiHelperImpl(private val newsApi: NewsApi): ApiHelper {
    override suspend fun getNews(): NewsResponse = newsApi.getNews()
}