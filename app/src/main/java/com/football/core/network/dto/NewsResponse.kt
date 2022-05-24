package com.football.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsResponse(@field:Json(name = "news") val news: List<NewsDto>)