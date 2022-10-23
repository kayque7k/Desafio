package com.teste.poc.application.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.teste.poc.application.data.api.LoverApi
import com.teste.poc.application.data.mapper.LoverMapper.toLover
import com.teste.poc.application.domain.model.Lover
import com.teste.poc.application.domain.repository.LoverRepository
import com.teste.poc.commons.extensions.QUALITY_IMAGE
import com.teste.poc.coreapi.DOCUMENT
import com.teste.poc.coreapi.FORM_DATA
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class LoverRepositoryImpl(
    private val loverApi: LoverApi,
    private val context: Context
) : LoverRepository {
    override suspend fun image(uri: Uri, lover: Lover): Lover = context.run {
        val archive = uri.path?.let { File(it) }
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY_IMAGE, bytes)

        val requestFile = RequestBody.create(
            FORM_DATA.toMediaTypeOrNull(),
            bytes.toByteArray()
        )

        loverApi.image(
            id = lover.id,
            file = MultipartBody.Part.createFormData(DOCUMENT, archive?.name, requestFile)
        ).toLover()
    }
}