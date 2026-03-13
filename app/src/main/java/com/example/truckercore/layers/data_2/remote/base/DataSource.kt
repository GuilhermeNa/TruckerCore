package com.example.truckercore.layers.data_2.remote.base

import com.google.firebase.firestore.DocumentReference

sealed class DataSource {

    data class Document(val value: DocumentReference): DataSource()

    data class Query(val value: com.google.firebase.firestore.Query): DataSource()

}