package com.example.truckercore.model.infrastructure.security.authentication.entity

import com.google.firebase.auth.FirebaseUser

data class NewEmailResult(
    val firebaseUser: FirebaseUser? = null,
    val userTaskSucceed: Boolean = false,
    val nameTaskSucceed: Boolean = false,
    val emailTaskSucceed: Boolean = false,
    val errors: Set<Exception>
) {

}