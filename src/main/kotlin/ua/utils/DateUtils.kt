package ua.utils

import java.text.SimpleDateFormat
import java.util.*

fun getCurrentDate(pattern: String = DATE_PATTERN): String {
    val sdf = SimpleDateFormat(pattern)
    return sdf.format(Date())
}

const val DATE_PATTERN = "dd/M/yyyy hh:mm:ss"