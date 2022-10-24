package com.wolfdeveloper.wolfdevlovers.application.usecase

import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Lover
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.LoverRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.safeRunDispatcher

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