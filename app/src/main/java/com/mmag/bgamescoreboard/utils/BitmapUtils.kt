package com.mmag.bgamescoreboard.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.InputStream

fun InputStream.toBitmap(): Bitmap {
    val image = BitmapFactory.decodeStream(this)
    if (image.byteCount <= 2500000) {
        return image
    } else {
        var compressedImage = image
        do {
            var scaleWidth = compressedImage.width / 2
            var scaleHeight = compressedImage.height / 2

            compressedImage =
                Bitmap.createScaledBitmap(image, scaleWidth, scaleHeight, true)
        } while (compressedImage.byteCount >= 2500000)
        return compressedImage
    }
}