package com.teste.renner.application.domain.usecase

import com.teste.renner.application.domain.model.Product
import com.teste.renner.application.domain.repository.ProductRepository
import com.teste.renner.application.usecase.ProductCategoryUseCase
import com.teste.renner.commons.CoroutinesTestRule
import com.teste.renner.commons.extensions.ZERO
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import org.assertj.core.api.Assertions
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductUseCaseTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private lateinit var useCase: ProductCategoryUseCase
    private val repo: ProductRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        useCase = ProductCategoryUseCase(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when execute use case return mock success`() {
        coEvery { repo.getProduct(ZERO) } returns Product.mock().first()

        val response = runBlocking { useCase.execute(0) }

        Assertions.assertThat(response).isEqualTo(Product.mock().first())
        coVerify(exactly = 1) { repo.getProduct(any()) }
    }

    @Test
    fun `when execute use case return mock error`() {
        coEvery { repo.getProduct(ZERO) } throws Exception(MOCK_EXCEPTION)

        Assertions.assertThatExceptionOfType(Exception::class.java)
            .isThrownBy {
                runBlocking {
                    useCase.execute(ZERO)
                }
            }
            .withMessage(MOCK_EXCEPTION)
    }

    companion object {
        private const val MOCK_EXCEPTION = "Mock Exception"
    }
}