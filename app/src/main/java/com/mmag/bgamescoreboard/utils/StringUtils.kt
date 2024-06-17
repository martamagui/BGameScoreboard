package com.mmag.bgamescoreboard.utils

fun String.capitalizeFirstLetter(): String {
    if (this.isEmpty()) return this
    return this.substring(0, 1).uppercase() + this.substring(1)
}