package com.example.bankingapp

data class PaymentUiState(
    val recipient: String = "",
    val accountNumber: String = "",
    val amount: String = "",
    val iban: String = "",
    val swift: String = "",
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val error: String? = null
)