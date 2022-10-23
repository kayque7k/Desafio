package com.teste.poc.application.data.api

import com.teste.poc.application.data.response.LoverResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface LoverApi {

    @Multipart
    @POST("lover/image/{id}")
    suspend fun image(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part
    ): LoverResponse
}