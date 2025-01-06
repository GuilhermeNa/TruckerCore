package com.example.truckercore.infrastructure.database.firebase.interfaces

import com.example.truckercore.shared.interfaces.Dto

internal interface FirebaseRepository {

    /**
     * Creates a new document in the specified Firestore collection using the provided DTO.
     * The DTO is assigned a new ID, and the document is saved to Firestore.
     *
     * @param collectionName The name of the Firestore collection.
     * @param dto The DTO to be saved.
     * @return The ID of the newly created document.
     */
    fun <T> create(collectionName: String, dto: Dto<T>): String

    /**
     * Updates an existing document in the specified Firestore collection with the provided DTO.
     * The DTO's ID is validated before updating the document.
     *
     * @param collectionName The name of the Firestore collection.
     * @param dto The DTO to update the document with.
     */
    fun <T> update(collectionName: String, dto: Dto<T>)

    /**
     * Deletes a document from the specified Firestore collection by ID.
     *
     * @param collectionName The name of the Firestore collection.
     * @param id The ID of the document to delete.
     */
    fun delete(collectionName: String, id: String)

}