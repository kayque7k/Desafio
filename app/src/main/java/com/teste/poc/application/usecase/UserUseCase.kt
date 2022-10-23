package com.teste.poc.application.usecase

import com.teste.poc.application.domain.repository.UserRepository
import com.teste.poc.commons.extensions.safeRunDispatcher
import com.teste.poc.coreapi.session.ISessionOutput

class UserUseCase(
    private val userRepository: UserRepository,
    private val output: ISessionOutput
) {
    suspend fun execute(load: () -> Unit) = safeRunDispatcher {
        userRepository.get(code = output.getCode(), load = load)
    }
}