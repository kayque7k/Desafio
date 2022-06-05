package com.teste.poc.application.usecase

import com.teste.poc.application.domain.repository.ProductRepository
import com.teste.poc.commons.extensions.safeRunDispatcher

class ProductUseCase(
    val productRepository: ProductRepository
) {
    suspend fun execute(id: Int) = safeRunDispatcher {
        productRepository.getProduct(id)
    }
}