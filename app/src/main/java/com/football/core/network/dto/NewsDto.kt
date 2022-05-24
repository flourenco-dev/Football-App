package com.football.core.network.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NewsDto(
    @field:Json(name = "title") val title: String,
    @field:Json(name = "image_url") val imageUrl: String,
    @field:Json(name = "resource_name") val resourceName: String,
    @field:Json(name = "resource_url") val resourceUrl: String,
    @field:Json(name = "news_link") val newsLink: String
)