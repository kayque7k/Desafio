package com.teste.renner.application.data.mockapi

import com.teste.renner.application.data.api.CategoryApi
import com.teste.renner.application.data.response.CategoryResponse

class CategoryApiMock : CategoryApi {

    override suspend fun getCategorys(): List<CategoryResponse> = CategoryResponse.mock()
}