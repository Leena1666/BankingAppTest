package com.example.bankingapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.ui.Modifier
import com.example.bankingapp.data.repository.PaymentRepositoryImpl
import com.example.bankingapp.domain.usecase.SendPaymentUseCase
import com.example.bankingapp.domain.usecase.ValidatePaymentUseCase
import com.example.bankingapp.ui.Demo
import com.example.bankingapp.ui.PaymentViewModel
import com.example.bankingapp.ui.theme.BankingAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = PaymentRepositoryImpl()
        val sendPaymentUseCase = SendPaymentUseCase(repository)
        val validatePaymentUseCase = ValidatePaymentUseCase()
        val viewModel = PaymentViewModel(sendPaymentUseCase, validatePaymentUseCase)

        setContent {
            BankingAppTheme {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(WindowInsets.statusBars.asPaddingValues())
                ) {
                    Demo(viewModel)
                }
            }
        }
    }
}
