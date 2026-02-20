package com.example.truckercore.core.my_lib.expressions

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.AbsoluteSizeSpan
import android.text.style.StyleSpan

fun String.span(
    word: String,
    size: Int? = null,
    bold: Boolean? = null
): SpannableString {
    val spannable = SpannableString(this)
    val start = indexOf(word)
    val end = start + word.length

    if (start >= 0) {
        //
        if (bold == true) {
            spannable.setSpan(
                StyleSpan(Typeface.BOLD),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }

        //
        size?.let {
            spannable.setSpan(
                AbsoluteSizeSpan(it, true),
                start,
                end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }
    return spannable
}