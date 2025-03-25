package com.example.truckercore.view.sealeds

sealed class PhoneAuthError : AppError() {
    data object InvalidCredentials : PhoneAuthError()
    data object LockedAccount : PhoneAuthError()
}