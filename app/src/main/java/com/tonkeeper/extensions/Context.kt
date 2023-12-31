package com.tonkeeper.extensions

import android.Manifest
import android.content.ClipData
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.tonapps.tonkeeperx.R
import uikit.extensions.withAlpha
import uikit.navigation.Navigation.Companion.navigation
import java.util.Locale

fun Context.copyWithToast(text: String) {
    navigation?.toast(getString(R.string.copied))
    copyToClipboard(text)
}

fun Context.clipboardText(): String {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = clipboard.primaryClip
    val text = clip?.getItemAt(0)?.text ?: ""
    return text.toString()
}

fun Context.copyToClipboard(text: String) {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
    val clip = ClipData.newPlainText("", text)
    clipboard.setPrimaryClip(clip)
}

fun Context.hasPushPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

fun Context.rateSpannable(rate: String, diff24h: String): SpannableString {
    val period = getString(R.string.period_24h)
    val span = SpannableString("$rate $diff24h $period")
    val color = getRateColor(diff24h)
    span.setSpan(
        ForegroundColorSpan(color),
        rate.length,
        span.length,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return span
}

@ColorInt
private fun Context.getRateColor(diff: String): Int {
    return getColor(diff.colorForChange).withAlpha(.64f)
}

val Context.currentLocale: Locale
    get() {
        return resources.configuration.locales[0]
    }