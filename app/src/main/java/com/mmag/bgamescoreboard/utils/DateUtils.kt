package com.mmag.bgamescoreboard.utils

import java.text.SimpleDateFormat
import java.util.Calendar

fun getCurrentDate(): String {
    val time = Calendar.getInstance().time
    val formatter = SimpleDateFormat("dd/MM/yyyy HH:mm")
    return formatter.format(time)
}