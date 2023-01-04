package com.example.pokedex.base

import com.example.pokedex.utils.Resource
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.io.IOException

abstract class BaseRepository() {

    suspend fun <T> safeApiCall(apiToBeCalled: suspend () -> Response<T>): Resource<T> {

        return withContext(Dispatchers.IO) {
            try {
                val response: Response<T> = apiToBeCalled()

                if (response.isSuccessful) {
                    Resource.Success(data = response.body()!!)
                } else {
                    val errorResponse: Error? =
                        convertErrorBody(response.errorBody())
                    Resource.Error(
                        errorMessage = errorResponse?.message ?: "Something went wrong"
                    )
                }
            } catch (e: HttpException) {
                Resource.Error(errorMessage = e.message ?: "Something went wrong")
            } catch (e: IOException) {
                Resource.Error("Please check your network connection")
            } catch (e: Exception) {
                Resource.Error(errorMessage = "Something went wrong")
            }
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): Error? {
        return try {
            errorBody?.source()?.let {
                val moshiAdapter = Moshi.Builder().build().adapter(Error::class.java)
                moshiAdapter.fromJson(it)
            }
        } catch (exception: Exception) {
            null
        }
    }
}
