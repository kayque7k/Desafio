package com.teste.poc.application.domain.repository

import android.net.Uri
import com.teste.poc.application.domain.model.Lover

interface LoverRepository {

    suspend fun image(uri: Uri, lover: Lover): Lover
}