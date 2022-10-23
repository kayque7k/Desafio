package com.teste.poc.commons.extensions

import android.app.Activity
import android.widget.Toast

const val EMPTY_STRING = ""
const val ZERO = 0
const val MAX_LINE_DEFAULT = 1
const val MAX_LINE_THREE = 3
const val QUALITY_IMAGE = 100

fun Int?.orZero() = this ?: ZERO

fun Int?.isZero() = this.orZero() == ZERO

fun Any?.isNull(): Boolean = this == null

fun Any?.isNotNull(): Boolean = this != null

fun exceptionToast(activity: Activity, id: Int) = activity.run {
    Toast.makeText(this, getString(id), Toast.LENGTH_LONG).show();
}
