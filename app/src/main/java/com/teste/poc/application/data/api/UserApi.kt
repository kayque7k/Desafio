package com.teste.poc.application.data.api

import com.teste.poc.application.data.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserApi {

    @Headers("Content-Type: application/json")
    @GET("user/get/{code}")
    suspend fun get(@Path("code") code: String): UserResponse
}