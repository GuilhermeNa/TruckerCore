package com.example.truckercore.model.infrastructure.data_source.firebase._firestore

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

object FirestoreTransactionHelper {

    private val firestore = FirebaseFirestore.getInstance()

    /**
     * Executes a Firestore transaction in a suspend function.
     *
     * @param T The type of result returned by the transaction.
     * @param block The transactional code block receiving the TransactionScope.
     * @return The result from the transaction block.
     */
    suspend fun <T> runTransaction(block: suspend TransactionScope.() -> T): T {
        return firestore.runTransaction { transaction ->
            // Wrap the Firestore transaction in a coroutine scope
            val scope = TransactionScope(transaction)
            kotlinx.coroutines.runBlocking {
                scope.block()
            }
        }.await()
    }

    /**
     * TransactionScope wraps Firestore's Transaction object,
     * providing suspend-friendly access and extensions.
     */
    class TransactionScope(private val transaction: com.google.firebase.firestore.Transaction) {

        suspend fun get(ref: com.google.firebase.firestore.DocumentReference) =
            transaction.get(ref)

        fun set(ref: com.google.firebase.firestore.DocumentReference, data: Any) {
            transaction.set(ref, data)
        }

        fun update(ref: com.google.firebase.firestore.DocumentReference, field: String, value: Any) {
            transaction.update(ref, field, value)
        }

        fun update(ref: com.google.firebase.firestore.DocumentReference, updates: Map<String, Any>) {
            transaction.update(ref, updates)
        }

        fun delete(ref: com.google.firebase.firestore.DocumentReference) {
            transaction.delete(ref)
        }
    }



}

suspend fun approveOrder(orderId: String) {
    val ordersRef = FirebaseFirestore.getInstance().collection("orders").document(orderId)

    FirestoreTransactionHelper.runTransaction {
        val snapshot = get(ordersRef)
        val status = snapshot.getString("status")

        if (status == "approved") {
            throw IllegalStateException("Order already approved")
        }

        update(ordersRef, "status", "approved")
    }
}