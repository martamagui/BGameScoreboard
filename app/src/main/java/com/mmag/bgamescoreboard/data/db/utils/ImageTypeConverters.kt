package com.mmag.bgamescoreboard.data.db.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import androidx.room.TypeConverter
import java.io.ByteArrayOutputStream

class ImageTypeConverters {
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?): ByteArray? {
        if (bitmap == null) {
            return null
        }
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }


    @TypeConverter
    fun getImageBitMap(byteArray: ByteArray?): Bitmap? {
        if (byteArray == null) {
            return null
        }
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }
}

