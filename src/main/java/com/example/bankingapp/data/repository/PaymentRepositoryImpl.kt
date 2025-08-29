package com.example.bankingapp.data.repository

import com.example.bankingapp.domain.model.PaymentData
import com.example.bankingapp.domain.repository.PaymentRepository
import kotlinx.coroutines.delay

class PaymentRepositoryImpl : PaymentRepository {
    override suspend fun sendPayment(paymentData: PaymentData): Result<Unit> {
        // Simulating API delay
        delay(1000)
        println("Payment sent: $paymentData")
        return Result.success(Unit)
    }
}