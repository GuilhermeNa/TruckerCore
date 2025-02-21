package com.example.truckercore.infrastructure.database.firebase.repository

import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

internal interface FirebaseAuthRepository {

    fun authenticateWithEmail(email: String, password: String): Flow<Response<String>>

    fun signIn(email: String, password: String): Flow<Response<Unit>>

    fun signOut()

    fun getCurrentUser(): FirebaseUser?

}