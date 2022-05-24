package com.football.core.network

import com.football.core.network.dto.NewsResponse

interface ApiHelper {
    suspend fun getNews(): NewsResponse
}