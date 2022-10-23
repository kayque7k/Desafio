package com.teste.poc.application.usecase

import android.net.Uri
import com.teste.poc.application.domain.model.Lover
import com.teste.poc.application.domain.repository.LoverRepository
import com.teste.poc.commons.extensions.safeRunDispatcher

class DashboardImageLoverUseCase(
    private val loverRepository: LoverRepository
) {
    suspend fun execute(uri: Uri,id: Int) = safeRunDispatcher {
        loverRepository.image(
            uri = uri,
            lover = Lover().apply {
                this.id = id
            }
        )
    }
}