package vn.vngalaxy.fas.shared.extensions

import java.util.Locale

fun String?.isNotEmptyBlank() = !this.isNullOrEmpty() && this.isNotBlank()

fun String.isValidPhoneNumber(): Boolean {
    val regexPhoneNumber = Regex("(03|05|07|08|09|01[2|6|8|9])+([0-9]{8})")
    return regexPhoneNumber.matches(this)
}

fun Any?.toDoubleOrDefault(default: Double = 0.0): Double {
    val value = this.toString()
    return value.toDoubleOrNull() ?: default
}

fun String.capitalize(): String {
    return this.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() }
}
