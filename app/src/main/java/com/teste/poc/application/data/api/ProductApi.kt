package com.teste.poc.application.data.api

import com.teste.poc.application.data.response.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {

    @GET("")
    suspend fun getProducts(
        @Query("idCategory") idCategory: Int
    ): List<ProductResponse>

    @GET("")
    suspend fun getProduct(@Path("id") id: Int): ProductResponse
}