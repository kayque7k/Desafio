package com.wolfdeveloper.wolfdevlovers.application.usecase

import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Post
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.PostRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.safeRunDispatcher

class DashboardImageLoverUseCase(
    private val postRepository: PostRepository
) {
    suspend fun execute(uri: Uri,id: Int) = safeRunDispatcher {
        postRepository.image(
            uri = uri,
            post = Post().apply {
                this.id = id
            }
        )
    }
}