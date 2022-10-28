package com.wolfdeveloper.wolfdevlovers.coreapi.session

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.wolfdeveloper.wolfdevlovers.application.domain.model.User
import com.wolfdeveloper.wolfdevlovers.commons.extensions.EMPTY_STRING
import java.sql.Timestamp


class Session(context: Context, val gson: Gson) : ISessionOutput, ISessioInput {

    private val sharedPreferences: SharedPreferences

    init {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        sharedPreferences = EncryptedSharedPreferences.create(
            SECRET_FILE,
            masterKeyAlias,
            context,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    override fun getUser() =
        gson.fromJson(sharedPreferences.getString(USER_ROUTER, EMPTY_STRING), User::class.java)

    override fun getCode() = sharedPreferences.getString(CODE_ROUTER, EMPTY_STRING).orEmpty()

    override fun getDateFinal(): Timestamp = getUser().dateLife

    override fun isValidade(): Boolean = getUser().run {
        try {
            dateLife.time > dateCreated.time
        } catch (e: Exception) {
            false
        }
    }

    override fun setUser(user: User?) = sharedPreferences.edit {
        putString(USER_ROUTER, gson.toJson(user)).apply()
    }

    override fun setCode(code: String) = sharedPreferences.edit {
        putString(CODE_ROUTER, code).apply()
    }

    companion object {
        private const val SECRET_FILE = "secret_shared_file_prefs"

        private const val USER_ROUTER = "USER_ROUTER"
        private const val CODE_ROUTER = "CODE_ROUTER"
    }
}