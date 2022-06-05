package com.teste.poc.application.data.api

import com.teste.poc.application.data.response.CategoryResponse
import retrofit2.http.GET

interface CategoryApi {

    @GET("")
    suspend fun getCategorys(
    ): List<CategoryResponse>

}