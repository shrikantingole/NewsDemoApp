package com.shrikant.domain.news

public data class NewsRes(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)