package com.wolfdeveloper.wolfdevlovers.application.data.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.wolfdeveloper.wolfdevlovers.application.data.api.UserApi
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.UserMapper.toUser
import com.wolfdeveloper.wolfdevlovers.application.data.mapper.UserMapper.toUserInput
import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.application.domain.repository.UserRepository
import com.wolfdeveloper.wolfdevlovers.commons.extensions.QUALITY_IMAGE
import com.wolfdeveloper.wolfdevlovers.commons.extensions.isNull
import com.wolfdeveloper.wolfdevlovers.commons.extensions.isZero
import com.wolfdeveloper.wolfdevlovers.coreapi.intercept.BASE
import com.wolfdeveloper.wolfdevlovers.coreapi.intercept.DOCUMENT
import com.wolfdeveloper.wolfdevlovers.coreapi.intercept.FORM_DATA
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessioInput
import com.wolfdeveloper.wolfdevlovers.coreapi.session.ISessionOutput
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File

class UserRepositoryImpl(
    private val productApi: UserApi,
    private val output: ISessionOutput,
    private val input: ISessioInput,
    private val context: Context
) : UserRepository {

    override suspend fun get(code: String): User? {
        var user = output.getUser()
        if (user.isNull()) {
            user = productApi.get(code).toUser()
            if (user.id.isZero()) {
                user = null
            } else {
                user.myImage = "${BASE}user/image/${code}"
                user.backgroundImage = "${BASE}user/background/${code}"
                user.posts.forEach {
                    it.image = "${BASE}lover/image/${it.id}/$code"
                }
            }
            input.setUser(user)
        }
        return user
    }

    override suspend fun insert(user: User): User =
        productApi.insert(user = user.toUserInput()).toUser()

    override suspend fun profile(uri: Uri, user: User): User = context.run {
        val archive = uri.path?.let { File(it) }
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY_IMAGE, bytes)

        val requestFile = RequestBody.create(
            FORM_DATA.toMediaTypeOrNull(),
            bytes.toByteArray()
        )

        productApi.profile(
            code = user.code,
            file = MultipartBody.Part.createFormData(DOCUMENT, archive?.name, requestFile)
        ).toUser()
    }

    override suspend fun background(uri: Uri, user: User): User = context.run {
        val archive = uri.path?.let { File(it) }
        val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
        val bytes = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, QUALITY_IMAGE, bytes)

        val requestFile = RequestBody.create(
            FORM_DATA.toMediaTypeOrNull(),
            bytes.toByteArray()
        )

        return productApi.background(
            code = user.code,
            file = MultipartBody.Part.createFormData(DOCUMENT, archive?.name, requestFile)
        ).toUser()
    }
}