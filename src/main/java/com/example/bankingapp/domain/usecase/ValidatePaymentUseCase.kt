package com.example.bankingapp.domain.usecase

import com.example.bankingapp.ui.TransferType
import com.example.bankingapp.domain.model.PaymentData

class ValidatePaymentUseCase {
    operator fun invoke(data: PaymentData, transferType: TransferType): String? {
        if (data.recipient.isBlank()) return "Recipient name is required"
        if (data.accountNumber.isBlank()) return "Account number is required"
        if (!data.accountNumber.all { it.isDigit() }) return "Account number must be numeric"

        if (data.amount.isBlank() || data.amount.toDoubleOrNull() == null || data.amount.toDouble() <= 0) {
            return "Enter a valid amount"
        }

        if (transferType is TransferType.International) {
            if (data.iban.isNullOrBlank() || data.iban.length != 34) {
                return "IBAN must be 34 characters long"
            }
            val swiftRegex = Regex("^[A-Z]{4}-[A-Z]{2}-[A-Z]{2}-\\d{2}$")
            if (data.swift.isNullOrBlank() || !swiftRegex.matches(data.swift)) {
                return "Invalid SWIFT format (e.g., AAAA-BB-CC-12)"
            }
        }
        return null
    }
}