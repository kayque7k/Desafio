package com.teste.poc.application.data.repository

import com.teste.poc.application.data.api.ProductApi
import com.teste.poc.application.data.response.ProductResponse
import com.teste.poc.application.domain.repository.ProductRepository
import com.teste.poc.commons.extensions.ZERO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test

class ProductRepositoryTest {

    private lateinit var repo: ProductRepository
    private val api: ProductApi = mockk(relaxed = true)

    @Before
    fun setup() {
        repo = ProductRepositoryImpl(api)
    }

    @Test
    fun `when call getProducts return mock success`() {
        coEvery { api.getProducts(ZERO) } returns ProductResponse.mock()

        runBlocking { repo.getProducts(ZERO) }

        coVerify(exactly = 1) { api.getProducts(any()) }
    }

    @Test
    fun `when call getProducts return mock error`() {
        coEvery { api.getProducts(ZERO) } throws Exception(MOCK_EXCEPTION)

        Assertions.assertThatExceptionOfType(Exception::class.java)
            .isThrownBy {
                runBlocking {
                    repo.getProducts(ZERO)
                }
            }
            .withMessage(MOCK_EXCEPTION)
    }

    @Test
    fun `when call getProduct return mock success`() {
        coEvery { api.getProduct(ZERO) } returns ProductResponse.mock().first()

        runBlocking { repo.getProduct(ZERO) }

        coVerify(exactly = 1) { api.getProduct(any()) }
    }

    @Test
    fun `when call getProduct return mock error`() {
        coEvery { api.getProduct(ZERO) } throws Exception(MOCK_EXCEPTION)

        Assertions.assertThatExceptionOfType(Exception::class.java)
            .isThrownBy {
                runBlocking {
                    repo.getProduct(ZERO)
                }
            }
            .withMessage(MOCK_EXCEPTION)
    }

    companion object {
        private const val MOCK_EXCEPTION = "Mock Exception"
    }

}