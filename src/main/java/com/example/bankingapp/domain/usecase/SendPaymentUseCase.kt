package com.example.bankingapp.domain.usecase

import com.example.bankingapp.domain.model.PaymentData
import com.example.bankingapp.domain.repository.PaymentRepository

class SendPaymentUseCase(private val repository: PaymentRepository) {
    suspend operator fun invoke(paymentData: PaymentData): Result<Unit> {
        if (paymentData.recipient.isBlank() || paymentData.accountNumber.isBlank()) {
            return Result.failure(IllegalArgumentException("Recipient & Account are required"))
        }
        if ((paymentData.iban?.length ?: 0) > 34) {
            return Result.failure(IllegalArgumentException("IBAN too long"))
        }
        return repository.sendPayment(paymentData)
    }
}