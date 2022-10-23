package com.teste.poc.application.usecase

import com.teste.poc.application.domain.model.User
import com.teste.poc.application.domain.repository.UserRepository
import com.teste.poc.commons.extensions.safeRunDispatcher

class DashboardInsertUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(user: User) = safeRunDispatcher {
        user.run {
            instagram = "$LINK_INSTAGRAM${instagram.replace("@", "").trim()}"
            userRepository.insert(this)
        }
    }

    companion object {
        private const val LINK_INSTAGRAM = "https://www.instagram.com/"
    }
}