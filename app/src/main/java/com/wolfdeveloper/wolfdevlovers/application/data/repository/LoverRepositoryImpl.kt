package com.wolfdeveloper.wolfdevlovers.application.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.data.api.LoverApi
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.LoverMapper.toLover
import com.wolfdeveloper.wolfdevlovers.application.domain.model.Lover
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.LoverRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.QUALITY_IMAGE
import com.wolfdeveloper.wolfdevlovers.coreapi.intercept.DOCUMENT
import com.wolfdeveloper.wolfdevlovers.coreapi.intercept.FORM_DATA
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