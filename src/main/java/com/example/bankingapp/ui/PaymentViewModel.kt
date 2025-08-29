package com.example.bankingapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bankingapp.PaymentUiState
import com.example.bankingapp.domain.model.PaymentData
import com.example.bankingapp.domain.usecase.SendPaymentUseCase
import com.example.bankingapp.domain.usecase.ValidatePaymentUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentViewModel(
    private val sendPaymentUseCase: SendPaymentUseCase,
    private val validatePaymentUseCase: ValidatePaymentUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentUiState())
    val uiState: StateFlow<PaymentUiState> = _uiState

    fun updateRecipient(value: String) {
        _uiState.value = _uiState.value.copy(recipient = value, error = null)
    }
    fun updateAccount(value: String) {
        _uiState.value = _uiState.value.copy(accountNumber = value, error = null)
    }
    fun updateAmount(value: String) {
        _uiState.value = _uiState.value.copy(amount = value, error = null)
    }
    fun updateIban(value: String) {
        _uiState.value = _uiState.value.copy(iban = value, error = null)
    }
    fun updateSwift(value: String) {
        _uiState.value = _uiState.value.copy(swift = value, error = null)
    }

    fun resetFields() {
        _uiState.value = PaymentUiState()
    }

    fun sendPayment(transferType: TransferType) {
        val currentState = _uiState.value

        val paymentData = PaymentData(
            recipient = currentState.recipient,
            accountNumber = currentState.accountNumber,
            amount = currentState.amount,
            iban = currentState.iban,
            swift = currentState.swift
        )

        val error = validatePaymentUseCase(paymentData, transferType)
        if (error != null) {
            _uiState.value = currentState.copy(error = error, success = false)
            return
        }

        viewModelScope.launch {
            _uiState.value = currentState.copy(isLoading = true, error = null, success = false)

            val result = sendPaymentUseCase(paymentData)

            _uiState.value = if (result.isSuccess) {
                _uiState.value.copy(isLoading = false, success = true)
            } else {
                _uiState.value.copy(
                    isLoading = false,
                    error = result.exceptionOrNull()?.message ?: "Unknown error"
                )
            }
        }
    }
}
