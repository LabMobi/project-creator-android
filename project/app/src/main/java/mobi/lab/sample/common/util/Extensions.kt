@file:Suppress("unused")

package mobi.lab.sample.common.util

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import androidx.annotation.StringRes
import mobi.lab.sample.R
import mobi.lab.sample.domain.entities.DomainException
import mobi.lab.sample.domain.entities.ErrorCode

fun SpannableString.addSpan(spannablePart: String, span: Any): SpannableString {
    val start = this.indexOf(spannablePart)
    val end = start + spannablePart.length
    if (start > -1) {
        setSpan(span, start, end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
    }
    return this
}

fun Throwable.errorCode(): ErrorCode = if (this is DomainException) code else ErrorCode.UNKNOWN

fun ErrorCode.formatErrorCode(context: Context?, @StringRes default: Int = R.string.error_generic): String = formatErrorCode(context, this, default)
