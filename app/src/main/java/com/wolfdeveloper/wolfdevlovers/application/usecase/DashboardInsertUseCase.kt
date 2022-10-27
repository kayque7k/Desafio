package com.wolfdeveloper.wolfdevlovers.application.usecase

import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.UserRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import com.wolfdeveloper.wolfdevlovers.commons.extensions.safeRunDispatcher

class DashboardInsertUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(user: User) = safeRunDispatcher {
        user.run {
            socialMediaLink = "$LINK_INSTAGRAM${socialMediaLink.replace("@", EMPTY_STRING).trim()}"
            userRepository.insert(this)
        }
    }

    companion object {
        private const val LINK_INSTAGRAM = "https://www.instagram.com/"
    }
}