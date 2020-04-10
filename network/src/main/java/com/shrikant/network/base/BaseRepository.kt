package com.shrikant.network.base

import com.shrikant.network.extension.Results
import java.io.IOException
import retrofit2.Response


open class BaseRepository() {
    suspend fun <T : Any> safeApiCall(
        call: suspend () -> Response<T>,
        errorMessage: String
    ): Results<T> = try {
        val response = call.invoke()
        if (response.isSuccessful && response.code() == 200) {
            Results.Success(response.body()!!)
        } else if (response.code() == 404) {
            Results.Error(IOException("Internal Server Error"))
        } else if (response.errorBody() != null) {
            Results.Error(IOException(response.errorBody()?.toString()))
        } else {
            Results.Error(IOException("Something error..."))
        }
    } catch (e: Exception) {
        Results.Error(IOException(errorMessage, e))
    }

    val <T> T.exhaustive: T get() = this
}