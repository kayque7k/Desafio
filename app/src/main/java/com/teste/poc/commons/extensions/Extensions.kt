package com.teste.poc.commons.extensions

import android.app.Activity
import android.content.Intent

const val EMPTY_STRING = ""
const val ZERO = 0
const val MAX_LINE_DEFAULT = 1
const val MAX_LINE_THREE = 3

fun Int?.orZero() = this ?: ZERO

fun Any?.isNull(): Boolean = this == null

fun Activity.startServiceVerify(intent: Intent) = startForegroundService(intent)

