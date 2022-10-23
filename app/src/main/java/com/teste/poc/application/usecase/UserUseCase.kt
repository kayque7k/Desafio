package com.teste.poc.application.usecase

import com.teste.poc.application.domain.repository.UserRepository
import com.teste.poc.commons.extensions.safeRunDispatcher


class UserUseCase(
    private val personRepository: UserRepository
) {
    suspend fun execute() =  safeRunDispatcher {
        personRepository.get("20221022204319545")
    }
}