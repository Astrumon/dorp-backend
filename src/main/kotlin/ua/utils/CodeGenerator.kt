package ua.utils

fun generateRandomCode(length: Int): String {
    val charset = ('A'..'Z') + ('0'..'9')
    return (1..length)
        .map { charset.random() }
        .joinToString("")
}