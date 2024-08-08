package ru.cityron.domain.utils

fun String.isValidIPAddress(): Boolean =
    matches("((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)".toRegex())

fun String.isValidTime(): Boolean =
    matches("([01][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])".toRegex())

fun String.isValidDate(): Boolean =
    matches("0[1-9]|[12][0-9]|3[01]\\.(0[1-9]|1[0-2])\\.[12][09][0-9][0-9]".toRegex())

fun String.isValidMac(): Boolean =
    matches("([0-9A-Fa-f]{2}):([0-9A-Fa-f]{2})".toRegex())