package com.teste.poc.application.data.mockapi

import com.teste.poc.application.data.api.CategoryApi
import com.teste.poc.application.data.response.CategoryResponse

class CategoryApiMock : CategoryApi {

    override suspend fun getCategorys(): List<CategoryResponse> = CategoryResponse.mock()
}