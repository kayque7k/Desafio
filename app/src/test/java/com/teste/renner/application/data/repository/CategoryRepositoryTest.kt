package com.teste.renner.application.data.repository

import com.teste.renner.application.data.api.CategoryApi
import com.teste.renner.application.data.api.ProductApi
import com.teste.renner.application.data.mapper.CategoryMapper.toCategoryList
import com.teste.renner.application.data.mapper.ProductMapper.toProduct
import com.teste.renner.application.data.mapper.ProductMapper.toProductList
import com.teste.renner.application.data.response.CategoryResponse
import com.teste.renner.application.data.response.ProductResponse
import com.teste.renner.application.domain.repository.CategoryRepository
import com.teste.renner.application.domain.repository.ProductRepository
import com.teste.renner.commons.extensions.EMPTY_STRING
import com.teste.renner.commons.extensions.ZERO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class CategoryRepositoryTest {

    private lateinit var repo: CategoryRepository
    private val api: CategoryApi = mockk(relaxed = true)

    @Before
    fun setup() {
        repo = CategoryRepositoryImpl(api)
    }

    @Test
    fun `when call getCategorys return mock success`() {
        coEvery { api.getCategorys() } returns CategoryResponse.mock()

        val response = runBlocking { repo.getCategorys() }

        Assertions.assertThat(response).isEqualTo(CategoryResponse.mock().toCategoryList())
        coVerify(exactly = 1) { api.getCategorys() }
    }

    @Test
    fun `when call getCategorys return mock error`() {
        coEvery { api.getCategorys() } throws Exception(MOCK_EXCEPTION)

        Assertions.assertThatExceptionOfType(Exception::class.java)
            .isThrownBy {
                runBlocking {
                    repo.getCategorys()
                }
            }
            .withMessage(MOCK_EXCEPTION)
    }

    companion object {
        private const val MOCK_EXCEPTION = "Mock Exception"
    }

}