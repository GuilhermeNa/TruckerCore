package com.example.truckercore.model.infrastructure.data_source.firebase.auth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest

interface FirebaseAuthDataSource {

    suspend fun createUserWithEmail(email: String, password: String): FirebaseUser

    suspend fun sendEmailVerification()

    suspend fun updateUserProfile(profile: UserProfileChangeRequest)

    suspend fun signInWithEmail(email: String, password: String)

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

    suspend fun observeEmailValidation()

}