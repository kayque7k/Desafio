package com.teste.poc.application.data.api

import com.teste.poc.application.data.input.UserInput
import com.teste.poc.application.data.response.UserResponse
import okhttp3.MultipartBody
import retrofit2.http.*

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