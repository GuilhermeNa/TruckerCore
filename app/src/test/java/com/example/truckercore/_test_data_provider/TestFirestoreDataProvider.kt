package com.example.truckercore._test_data_provider

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
import io.mockk.mockk

class TestFirestoreDataProvider {

    fun docReference() = mockk<DocumentReference>(relaxed = true)

    fun query() = mockk<Query>(relaxed = true)

}