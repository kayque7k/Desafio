package com.teste.poc.application.usecase

import com.teste.poc.application.domain.repository.ProductRepository
import com.teste.poc.commons.extensions.safeRunDispatcher

class ProductCategoryUseCase(
    val productRepository: ProductRepository
) {
    suspend fun execute(idCategory: Int) = safeRunDispatcher {
        productRepository.getProducts(idCategory)
    }
}