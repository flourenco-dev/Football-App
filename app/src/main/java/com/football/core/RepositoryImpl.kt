package com.football.core

import com.football.core.network.ApiHelper
import com.football.core.network.dto.NewsDto
import com.football.model.News

internal class RepositoryImpl(private val apiHelper: ApiHelper): Repository {

    override suspend fun fetchNews(): List<News> = apiHelper.getNews().news.map {
        it.toNews()
    }

    private fun NewsDto.toNews(): News =
        News(
            title = title,
            imageUrl = imageUrl,
            resourceName = resourceName,
            resourceUrl = resourceUrl,
            newsLink = newsLink
        )
}