package com.shrikant.network.retrofit

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object ApiService {

    private val apiClient: ApiClient by lazy { provideRetrofitBuilder().create(ApiClient::class.java) }


    private fun provideRetrofitBuilder(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://newsapi.org/v2/")
            .client(provideOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory()).build()
    }


    private fun provideOkHttpClient(
    ): OkHttpClient {
        val httpClient = OkHttpClient().newBuilder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        httpClient.addInterceptor(loggingInterceptor)
        httpClient.addInterceptor(Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val url = original.url.newBuilder()
                .build()
            val request = original.newBuilder()
                .addHeader("Accept", "application/json")
                .url(url).build()

            chain.proceed(request)
        })
        return httpClient.build()
    }


    fun callNewsListAsync(page: Int) = apiClient.callNewsAsync(page)

}