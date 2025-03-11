package com.example.truckercore.view.home

import androidx.lifecycle.ViewModel
import com.example.truckercore.model.configs.app_constants.Collection
import com.google.firebase.firestore.FirebaseFirestore

internal class MainViewModel(
    private val firestore: FirebaseFirestore
): ViewModel() {

    fun teste() {
        val doc = firestore.collection(Collection.CENTRAL.name).document("myUserId")
        doc.id
    }

}