package com.teste.renner.application.data.api

import com.teste.renner.application.data.response.CategoryResponse
import retrofit2.http.GET

interface CategoryApi {

    @GET("")
    suspend fun getCategorys(
    ): List<CategoryResponse>

}