package com.wolfdeveloper.wolfdevlovers.application.domain.repository

import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Post

interface PostRepository {

    suspend fun image(uri: Uri, post: Post,code: String): Post
}