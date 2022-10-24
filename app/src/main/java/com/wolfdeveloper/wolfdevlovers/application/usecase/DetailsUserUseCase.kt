package com.wolfdeveloper.wolfdevlovers.application.usecase

import com.wolfdeveloper.wolfdevlovers.application.domain.repository.UserRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.safeRunDispatcher
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessionOutput

class DetailsUserUseCase(
    private val userRepository: UserRepository,
    private val output: ISessionOutput
) {
    suspend fun execute(load: () -> Unit) = safeRunDispatcher {
        userRepository.get(code = output.getCode(), load = load)
    }
}