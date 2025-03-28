package com.example.truckercore.business_admin.view_model.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.truckercore.model.infrastructure.security.authentication.entity.EmailAuthCredential
import com.example.truckercore.model.infrastructure.security.authentication.service.AuthService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmailAuthViewModel(
    private val authService: AuthService
): ViewModel() {

    fun createUserWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            val credential = EmailAuthCredential(email, password)
            val response = authService.createUserWithEmail(credential)

        }
    }



}