package com.wolfdeveloper.wolfdevlovers.application.domain.repository

import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Lover

interface LoverRepository {

    suspend fun image(uri: Uri, lover: Lover): Lover
}