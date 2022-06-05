package com.teste.poc.application.usecase

import com.teste.poc.application.domain.repository.CategoryRepository
import com.teste.poc.commons.extensions.safeRunDispatcher

class CategoryUseCase(
    val categoryRepository: CategoryRepository
) {
    suspend fun execute() = safeRunDispatcher {
        categoryRepository.getCategorys()
    }
}