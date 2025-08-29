package com.example.bankingapp.util

import java.text.DecimalFormat

fun String.formatAmount(): String {
    val cleaned = this.filter { it.isDigit() || it == '.' }
    if (cleaned.isEmpty()) return ""

    val number = cleaned.toDoubleOrNull() ?: return ""
    val formatter = DecimalFormat("#,###.##")
    return formatter.format(number)
}