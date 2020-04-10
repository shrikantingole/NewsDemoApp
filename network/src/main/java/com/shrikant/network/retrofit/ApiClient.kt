package com.shrikant.network.retrofit

import com.shrikant.domain.news.NewsRes
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiClient {
    @GET("top-headlines?country=in&category=health&apiKey=7e4199b9c96f43fda7a6569f41552690")
    fun callNewsAsync(@Query("page") page: Int): Deferred<Response<NewsRes>>
}
