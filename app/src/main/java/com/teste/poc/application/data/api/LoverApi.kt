package com.teste.poc.application.data.api

import com.teste.poc.application.data.response.LoverResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface LoverApi {

    @Multipart
    @PUT("lover/image/{id}")
    suspend fun image(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part
    ): LoverResponse
}