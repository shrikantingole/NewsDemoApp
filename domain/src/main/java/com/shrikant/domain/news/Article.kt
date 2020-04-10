package com.shrikant.domain.news

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "article")
data class Article(
    @ColumnInfo(name = "author")
    val author: String? = "",
    @ColumnInfo(name = "content")
    val content: String? = "",
    @ColumnInfo(name = "description")
    val description: String? = "",
    @ColumnInfo(name = "publishedAt")
    val publishedAt: String? = "",
    @ColumnInfo(name = "title")
    val title: String? = "",
    @ColumnInfo(name = "url")
    @PrimaryKey
    val url: String = "",
    @ColumnInfo(name = "urlToImage")
    val urlToImage: String? = ""
) : Serializable