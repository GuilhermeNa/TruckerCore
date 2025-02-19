package com.example.truckercore.infrastructure.database.firebase.repository

import com.example.truckercore.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

internal class FirebaseAuthImpl(
    private val fireBaseAuth: com.google.firebase.auth.FirebaseAuth
) : FirebaseAuth {

    override fun authenticateUser(email: String, password: String) = callbackFlow {
        fireBaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                task.exception?.let { error ->
                    this.close(error)
                    throw error
                }

                task.result.user?.let { user ->
                    val result = Response.Success(user.uid)
                    trySend(result)
                } ?: throw IncompleteTaskException(
                    "The task did not complete successfully." +
                            " User authentication failed."
                )
            }

        awaitClose { this.cancel() }
    }

    override fun signIn(email: String, password: String): Flow<Response<Unit>> {
        fireBaseAuth.signInWithEmailAndPassword(email, password)
        //TODO()
    }

    override fun signOut() {
        TODO("Not yet implemented")
    }

    override fun getCurrentUser(): FirebaseUser? {
        fireBaseAuth.currentUser.getIdToken()
    }


}