package com.example.bankingapp.domain.model

data class PaymentData(
    val recipient: String,
    val accountNumber: String,
    val amount: String,
    val iban: String? = null,
    val swift: String? = null
)