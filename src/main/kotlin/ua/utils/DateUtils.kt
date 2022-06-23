package ua.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(pattern: String): String {
    val sdf = SimpleDateFormat(pattern)
    return sdf.format(Date())
}