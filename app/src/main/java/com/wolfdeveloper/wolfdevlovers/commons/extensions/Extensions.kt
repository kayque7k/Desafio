package com.wolfdeveloper.wolfdevlovers.commons.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.wolfdeveloper.wolfdevlovers.R
import com.wolfdeveloper.wolfdevlovers.application.feature.detail.DetailViewModel
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

const val EMPTY_STRING = ""
const val ZERO = 0
const val ZERO_LONG = 0L
const val MAX_LINE_DEFAULT = 1
const val MAX_LINE_THREE = 3
const val QUALITY_IMAGE = 100
const val UTF_8 = "UTF-8"
const val ISO_8859_1 = "ISO-8859-1"

private const val URL_YOUTUBE = "https://www.youtube.com/watch?v="

fun Int?.orZero() = this ?: ZERO

fun Long?.orZero() = this ?: ZERO_LONG

fun Int?.isZero() = this.orZero() == ZERO

fun Int?.isPositive() = this.orZero() >= ZERO

fun Any?.isNull(): Boolean = this == null

fun Any?.isNotNull(): Boolean = this != null

fun exceptionToast(activity: Activity, id: Int) = activity.run {
    Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show()
}

fun Timestamp.formater(): String {
    return try {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
        sdf.format(Date(this.time))
    } catch (e: Exception) {
        EMPTY_STRING
    }
}

fun openLink(activity: Activity, url: String) {
    val intentApp = Intent(
        Intent.ACTION_VIEW, Uri.parse(url.replace(URL_YOUTUBE, EMPTY_STRING))
    )
    val intentBrowser = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    try {
        activity.startActivity(intentApp)
    } catch (ex: ActivityNotFoundException) {
        try {
            activity.startActivity(intentBrowser)
        } catch (ex: Exception) {
            exceptionToast(activity, R.string.error_link)
        }
    }
}
