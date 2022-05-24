package com.teste.renner.application.usecase

import com.teste.renner.application.domain.repository.CategoryRepository
import com.teste.renner.commons.extensions.safeRunDispatcher

class CategoryUseCase(
    val categoryRepository: CategoryRepository
) {
    suspend fun execute() = safeRunDispatcher {
        categoryRepository.getCategorys()
    }
}