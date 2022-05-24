package com.football.core

import com.football.model.News

interface Repository {
    suspend fun fetchNews(): List<News>
}