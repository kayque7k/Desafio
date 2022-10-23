package com.teste.poc.application.domain.repository

import com.teste.poc.application.domain.model.User

interface UserRepository {

    suspend fun get(code: String): User
}