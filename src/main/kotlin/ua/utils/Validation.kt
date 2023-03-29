package ua.utils

object Validation {

    fun isValidSessionCode(code: String, length: Int = 6): Boolean {
        return code.length == length && code.all { it in 'A'..'Z' || it in '0'..'9' }
    }
}

fun String?.isNullOrEmptyString() = isNullOrEmpty() || equals("null")