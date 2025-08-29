package com.example.bankingapp.domain.repository

import com.example.bankingapp.domain.model.PaymentData


interface PaymentRepository {
    suspend fun sendPayment(paymentData: PaymentData): Result<Unit>
}