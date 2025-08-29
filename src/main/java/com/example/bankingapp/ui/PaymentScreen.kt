package com.example.bankingapp.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.bankingapp.util.formatAmount

sealed class TransferType(val title: String) {
    data object Domestic : TransferType("Domestic Transfer")
    data object International : TransferType("International Transfer")
}

@Composable
fun PaymentTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = modifier.fillMaxWidth(),
        singleLine = true,
        maxLines = 1,
        keyboardOptions = keyboardOptions
    )
}

@Composable
fun PaymentScreen(
    transferType: TransferType,
    viewModel: PaymentViewModel
) {
    val state = viewModel.uiState.collectAsState().value
    var amount by remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(transferType.title, style = MaterialTheme.typography.headlineSmall)

        PaymentTextField(
            label = "Recipient Name",
            value = state.recipient,
            onValueChange = { viewModel.updateRecipient(it) }
        )

        PaymentTextField(
            label = "Account Number",
            value = state.accountNumber,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { input ->
                val digitsOnly = input.filter { it.isDigit() }
                viewModel.updateAccount(digitsOnly)
            }
        )

        PaymentTextField(
            label = "Amount",
            value = amount.text,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            onValueChange = { newText ->
                val cleaned = newText.filter { it.isDigit() || it == '.' }
                viewModel.updateAmount(cleaned)
                val formatted = cleaned.formatAmount()
                amount = TextFieldValue(
                    text = formatted,
                    selection = TextRange(formatted.length)
                )
            }
        )

        if (transferType is TransferType.International) {
            PaymentTextField(
                label = "IBAN",
                value = state.iban,
                onValueChange = { input ->
                    val cleaned = input.uppercase()
                        .filter { it.isLetterOrDigit() }
                        .take(34)
                    viewModel.updateIban(cleaned)
                }
            )

            PaymentTextField(
                label = "SWIFT Code",
                value = state.swift,
                onValueChange = { input ->
                    val cleaned = input.uppercase()
                        .filter { it.isLetterOrDigit() || it == '-' }
                        .take(11)
                    viewModel.updateSwift(cleaned)
                }
            )
        }

        Button(
            onClick = { viewModel.sendPayment(transferType) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !state.isLoading
        ) {
            Text(if (state.isLoading) "Sending..." else "Send Payment")
        }

        state.error?.let {
            Text("Error: $it", color = MaterialTheme.colorScheme.error)
        }
        if (state.success) {
            Text("Payment Successful!", color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
fun Demo(viewModel: PaymentViewModel) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Domestic", "International")

    Column(Modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = selectedTabIndex) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                        viewModel.resetFields()
                    },
                    text = { Text(title) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        val transferType =
            if (selectedTabIndex == 0) TransferType.Domestic else TransferType.International

        PaymentScreen(
            transferType = transferType,
            viewModel = viewModel
        )
    }
}