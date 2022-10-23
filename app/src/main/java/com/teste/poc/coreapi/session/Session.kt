package com.teste.poc.coreapi.session

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.google.gson.Gson
import com.teste.poc.application.domain.model.User
import com.teste.poc.commons.extensions.EMPTY_STRING


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
        gson.fromJson(sharedPreferences.getString(PERSON_ROUTER, EMPTY_STRING), User::class.java)

    override fun setUser(user: User) {
        sharedPreferences.edit().putString(PERSON_ROUTER, gson.toJson(user)).apply()
    }


    companion object {
        private const val SECRET_FILE = "secret_shared_file_prefs"

        private const val PERSON_ROUTER = "PERSON_ROUTER"
    }
}