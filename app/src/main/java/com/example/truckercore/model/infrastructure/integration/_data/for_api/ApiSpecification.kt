package com.example.truckercore.model.infrastructure.integration._data.for_api

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query

sealed class ApiSpecification {

    data class FirestoreDocumentReference(val doc: DocumentReference) : ApiSpecification()

    data class FirestoreQuery(val query: Query) : ApiSpecification()

}