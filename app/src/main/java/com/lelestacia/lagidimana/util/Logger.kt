package com.lelestacia.lagidimana.util

import android.content.ContentValues.TAG
import android.util.Log

class Logger {

    fun info(className: ClassName? = null, message: Message) {
        if (className != null) {
            Log.i(className.value, "[${className.value}] ${message.value}")
        } else {
            Log.i(TAG, message.value)
        }
    }

    fun debug(className: ClassName? = null, message: Message) {
        if (className != null) {
            Log.d(className.value, "[${className.value}] ${message.value}")
        } else {
            Log.d(TAG, message.value)
        }
    }

    fun error(className: ClassName? = null, message: Message) {
        if (className != null) {
            Log.e(className.value, "[${className.value}] ${message.value}")
        } else {
            Log.e(TAG, message.value)
        }
    }

    fun warning(className: ClassName? = null, message: Message) {
        if (className != null) {
            Log.w(className.value, "[${className.value}] ${message.value}")
        } else {
            Log.w(TAG, message.value)
        }
    }
}

@JvmInline
value class ClassName(val value: String)

@JvmInline
value class Message(val value: String)