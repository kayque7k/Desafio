package com.wolfdeveloper.wolfdevlovers.application.data.api

import com.wolfdeveloper.wolfdevlovers.application.data.response.LoverResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Part

interface LoverApi {

    @Multipart
    @PUT("lover/image/{id}")
    suspend fun image(
        @Path("id") id: Int,
        @Part file: MultipartBody.Part
    ): LoverResponse
}