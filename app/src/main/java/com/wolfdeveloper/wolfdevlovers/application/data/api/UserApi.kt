package com.wolfdeveloper.wolfdevlovers.application.data.api

import com.wolfdeveloper.wolfdevlovers.application.data.input.UserInput
import com.wolfdeveloper.wolfdevlovers.application.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

interface UserApi {

    @Headers("Content-Type: application/json")
    @GET("user/get/{code}")
    suspend fun get(@Path("code") code: String): UserResponse

    @Headers("Content-Type: application/json")
    @POST("user/insert")
    suspend fun insert(@Body user: UserInput): UserResponse

    @Multipart
    @PUT("user/upload/image/{code}")
    suspend fun profile(
        @Path("code") code: String,
        @Part file: MultipartBody.Part
    ): UserResponse

    @Multipart
    @PUT("user/upload/background/{code}")
    suspend fun background(
        @Path("code") code: String,
        @Part file: MultipartBody.Part
    ): UserResponse
}