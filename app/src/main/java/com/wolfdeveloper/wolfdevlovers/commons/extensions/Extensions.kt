package com.wolfdeveloper.wolfdevlovers.commons.extensions

import android.app.Activity
import android.widget.Toast
import java.sql.Date
import java.sql.Timestamp
import java.text.SimpleDateFormat

const val EMPTY_STRING = ""
const val ZERO = 0
const val MAX_LINE_DEFAULT = 1
const val MAX_LINE_THREE = 3
const val QUALITY_IMAGE = 100

fun Int?.orZero() = this ?: ZERO

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
