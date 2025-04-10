package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.google.firebase.auth.FirebaseUser

data class NewEmailResult(
    val firebaseUser: FirebaseUser? = null,
    val userCreated: Boolean = false,
    val nameUpdated: Boolean = false,
    val emailSent: Boolean = false,
    val errors: Set<Exception>
)