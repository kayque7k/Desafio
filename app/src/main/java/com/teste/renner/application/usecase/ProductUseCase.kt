package com.teste.renner.application.usecase

import com.teste.renner.application.domain.repository.ProductRepository
import com.teste.renner.commons.extensions.safeRunDispatcher

class ProductUseCase(
    val productRepository: ProductRepository
) {
    suspend fun execute(id: Int) = safeRunDispatcher {
        productRepository.getProduct(id)
    }
}