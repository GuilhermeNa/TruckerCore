package com.example.truckercore.infrastructure.database.firebase.implementations

import com.example.truckercore.infrastructure.database.firebase.interfaces.FirebaseRepository
import com.example.truckercore.shared.interfaces.Dto
import com.google.firebase.firestore.FirebaseFirestore

internal class FirebaseRepositoryImpl(private val firestore: FirebaseFirestore) :
    FirebaseRepository {

    private fun getCollection(name: String) = firestore.collection(name)

    override fun <T> create(collectionName: String, dto: Dto<T>): String {
        val document = getCollection(collectionName).document()
        val newDto = dto.initializeId(document.id)
        document.set(newDto)
        return document.id
    }

    override fun <T> update(collectionName: String, dto: Dto<T>) {
        val document = getCollection(collectionName).document(dto.id!!)
        document.set(dto)
    }

    override fun delete(collectionName: String, id: String) {
        getCollection(collectionName).document(id).delete()
    }

}