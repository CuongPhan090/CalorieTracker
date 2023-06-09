package com.example.core.util

import android.content.Context

/**
 * We can pass 2 type of strings
 * 1st: String comes from app resources
 * 2nd: Dynamic string come from remote API
 */
sealed class UiText {
    data class DynamicString(val text: String): UiText()
    data class StringResource(val resId: Int): UiText()

    fun asString(context: Context): String {
        return when(this) {
            is DynamicString -> text
            is StringResource -> context.getString(resId)
        }
    }
}