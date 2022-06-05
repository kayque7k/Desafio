package com.teste.poc.application.data.repository

import com.teste.poc.application.data.api.CategoryApi
import com.teste.poc.application.data.mapper.CategoryMapper.toCategoryList
import com.teste.poc.application.data.response.CategoryResponse
import com.teste.poc.application.domain.repository.CategoryRepository
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