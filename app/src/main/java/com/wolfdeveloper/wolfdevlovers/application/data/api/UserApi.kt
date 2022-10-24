package com.wolfdeveloper.wolfdevlovers.application.data.api

import com.wolfdeveloper.wolfdevlovers.application.data.input.UserInput
import com.wolfdeveloper.wolfdevlovers.application.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.Headers
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Body
import retrofit2.http.Part

interface UserApi {

    @Headers("Content-Type: application/json")
    @GET("user/get/{code}")
    suspend fun get(@Path("code") code: String): UserResponse

    @Headers("Content-Type: application/json")
    @PUT("user/insert")
    suspend fun insert(@Body user: UserInput): UserResponse

    @Multipart
    @PUT("user/image/{code}")
    suspend fun profile(
        @Path("code") code: String,
        @Part file: MultipartBody.Part
    ): UserResponse

    @Multipart
    @PUT("user/background/{code}")
    suspend fun background(
        @Path("code") code: String,
        @Part file: MultipartBody.Part
    ): UserResponse
}