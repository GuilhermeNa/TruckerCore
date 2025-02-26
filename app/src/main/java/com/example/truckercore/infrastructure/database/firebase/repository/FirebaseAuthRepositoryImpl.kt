package com.example.truckercore.infrastructure.database.firebase.repository

import com.example.truckercore.infrastructure.database.firebase.errors.IncompleteTaskException
import com.example.truckercore.shared.utils.sealeds.Response
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

internal class FirebaseAuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : FirebaseAuthRepository {

    override fun authenticateWithEmail(email: String, password: String) = callbackFlow {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                task.exception?.let { error ->
                    this.close(error)
                }

                task.result.user?.let { user ->
                    val result = Response.Success(user.uid)
                    trySend(result)
                } ?: {
                    close(
                        IncompleteTaskException(
                            "The task did not complete successfully." +
                                    " User authentication failed."
                        )
                    )
                }

            }

        awaitClose { this.cancel() }
    }

    override fun signIn(email: String, password: String) = callbackFlow {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                task.exception?.let { error ->
                    this.close(error)
                }

                if (task.isSuccessful) trySend(Response.Success(Unit))
                else close(IncompleteTaskException("Failure when trying to login."))
            }

        awaitClose { this.cancel() }
    }

    override fun signOut() {
        firebaseAuth.signOut()
    }

    override fun getCurrentUser(): FirebaseUser? =
        firebaseAuth.currentUser

}