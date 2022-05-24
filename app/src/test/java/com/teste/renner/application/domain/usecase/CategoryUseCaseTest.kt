package com.teste.renner.application.domain.usecase

import com.teste.renner.application.domain.model.Category
import com.teste.renner.application.domain.repository.CategoryRepository
import com.teste.renner.application.usecase.CategoryUseCase
import com.teste.renner.commons.CoroutinesTestRule
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
class CategoryUseCaseTest {

    @get:Rule
    val coroutineTestRule = CoroutinesTestRule()

    private lateinit var useCase: CategoryUseCase
    private val repo: CategoryRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        useCase = CategoryUseCase(repo)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when execute use case return mock success`() {
        coEvery { repo.getCategorys() } returns Category.mock()

        runBlocking { useCase.execute() }

        coVerify(exactly = 1) { repo.getCategorys() }
    }

    @Test
    fun `when execute use case return mock error`() {
        coEvery { repo.getCategorys() } throws Exception(MOCK_EXCEPTION)

        Assertions.assertThatExceptionOfType(Exception::class.java)
            .isThrownBy {
                runBlocking {
                    useCase.execute()
                }
            }
            .withMessage(MOCK_EXCEPTION)
    }

    companion object {
        private const val MOCK_EXCEPTION = "Mock Exception"
    }
}