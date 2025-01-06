package br.com.digio.androidtest.presentation.utils

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import br.com.digio.androidtest.R

fun formatColoredText(
    baseText: String,
    highlightParts: List<String>,
    context: Context,
    highlightColorRes: Int = R.color.font_color_digio_cash,
    defaultColorRes: Int = R.color.blue_darker
): SpannableString {
    val spannable = SpannableString(baseText)
    val defaultColor = ContextCompat.getColor(context, defaultColorRes)
    val highlightColor = ContextCompat.getColor(context, highlightColorRes)

    spannable.setSpan(
        ForegroundColorSpan(defaultColor),
        0,
        baseText.length,
        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
    )

    highlightParts.forEach { part ->
        val startIndex = baseText.indexOf(part, ignoreCase = true)
        if (startIndex >= 0) {
            val endIndex = startIndex + part.length
            spannable.setSpan(
                ForegroundColorSpan(highlightColor),
                startIndex,
                endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    return spannable
}
