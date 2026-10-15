package com.appnatextassistant.core.share

import android.content.Context
import android.content.Intent

fun Context.shareText(text: String) {
    val sendIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, text)
    }
    startActivity(Intent.createChooser(sendIntent, null))
}
