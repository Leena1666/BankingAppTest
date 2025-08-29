# Banking App - Payment Screen (Kotlin + Jetpack Compose)

This project is an Android banking app that demonstrates a **reusable Payment Screen** for handling Domestic and International transfers.

## Features

- **Reusable Payment Screen**: One screen adapts for Domestic and International transfers
- **Domestic Transfer Fields**:
  - Recipient Name
  - Account Number
  - Amount
- **International Transfer Fields** (in addition to Domestic):
  - IBAN (34 characters)
  - SWIFT code (format: AAAA-BB-CC-12)
- **Validations** for all fields
- **Input restrictions**:
  - Numeric-only for Account Number and Amount
  - Max length for IBAN and SWIFT
  - Amount formatted with commas
- **Success and error messages** specific to each transfer type
- **Jetpack Compose UI** with tabs to switch between Domestic and International
- **MVVM + Clean Architecture principles**

---

## Principles Used

- **Clean Architecture**:  
  - `domain` layer for business models and use cases  
  - `data` layer for repository implementation  
  - `presentation` layer for UI and ViewModel  

- **MVVM (Model-View-ViewModel)**:  
  - ViewModel manages state and validation  
  - Composable UI observes state via `StateFlow`

- **Separation of Concerns**:  
  - Validation handled in `ValidatePaymentUseCase`  
  - Amount formatting and input filtering handled in the presentation layer  
  - Reusable components (PaymentTextField) for maintainable code

- **Reusability**:  
  - One Payment Screen adapts dynamically to Domestic or International transfers based on `TransferType`  

---

## Screenshots

Domestic Transfer Screen:  
![Domestic](https://github.com/Leena1666/BankingAppTest/blob/main/Screenshot_20250829_115901.png)

International Transfer Screen:  
![International](https://github.com/Leena1666/BankingAppTest/blob/main/Screenshot_20250829_115923.png)

Payment Success Message:  
![Success](https://github.com/Leena1666/BankingAppTest/blob/main/Screenshot_20250829_120630.png)

International Transfer Error (SWIFT Code Validation):  
![SWIFT Error](https://github.com/Leena1666/BankingAppTest/blob/main/Screenshot_20250829_122822.png)

## How to Run

1. Clone the repo:  
   ```bash
   git clone https://github.com/YOUR_USERNAME/BankingAppCompose.git
