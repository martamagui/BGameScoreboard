package com.mmag.bgamescoreboard.utils

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import com.mmag.bgamescoreboard.BuildConfig
import java.io.File

fun Context.createTempPictureUri(
    provider: String = "${BuildConfig.APPLICATION_ID}.provider",
    fileName: String = "picture_${System.currentTimeMillis()}",
    fileExtension: String = ".png"
): Uri {
    val tempFile = File.createTempFile(
        fileName, fileExtension, cacheDir
    ).apply {
        createNewFile()
    }

    return FileProvider.getUriForFile(applicationContext, provider, tempFile)
}