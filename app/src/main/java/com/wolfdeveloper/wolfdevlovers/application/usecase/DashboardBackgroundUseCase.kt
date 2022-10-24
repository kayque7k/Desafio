package com.wolfdeveloper.wolfdevlovers.application.usecase

import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.UserRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.safeRunDispatcher

class DashboardBackgroundUseCase(
    private val userRepository: UserRepository
) {
    suspend fun execute(uri: Uri,code: String) = safeRunDispatcher {
        userRepository.background(
            uri = uri,
            user = User().apply {
                this.code = code
            }
        )
    }
}