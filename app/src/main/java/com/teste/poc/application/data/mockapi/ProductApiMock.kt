package com.teste.poc.application.data.mockapi

import com.teste.poc.application.data.api.ProductApi
import com.teste.poc.application.data.response.ProductResponse

class ProductApiMock : ProductApi {

    override suspend fun getProduct(id: Int): ProductResponse =
        ProductResponse.mock().filter { it.id == id }.first()


    override suspend fun getProducts(idCategory: Int): List<ProductResponse> =
        ProductResponse.mock().filter { it.idCategory == idCategory }
}