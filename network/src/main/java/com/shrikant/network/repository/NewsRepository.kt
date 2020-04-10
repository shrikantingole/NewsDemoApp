package com.shrikant.network.repository

import android.content.Context
import com.shrikant.domain.news.Article
import com.shrikant.network.base.BaseRepository
import com.shrikant.network.database.NewsDatabase
import com.shrikant.network.retrofit.ApiService

object NewsRepository : BaseRepository() {

    suspend fun callNewsListAsync(page: Int) = safeApiCall(
        call = {
            ApiService.callNewsListAsync(page).await()
        },
        errorMessage = "Error occurred"
    )


    suspend fun callOfflineNews(context: Context): List<Article> {
        return NewsDatabase.getInstance(context.applicationContext).newsDao().getAllList()
    }

    suspend fun insertNews(list: List<Article>, context: Context, isFresh: Boolean): List<Article> {
        NewsDatabase.getInstance(context.applicationContext).newsDao().let {
            if (isFresh) it.deleteAll()
            it.insertAllArticle(list)
            return it.getAllList()
        }
    }


}