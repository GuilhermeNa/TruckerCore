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
     * @throws ValidatorNotFoundException If no validation strategy is registered for the given DTO type.
     * @throws CorruptedFileException If the DTO cannot be inserted due to validation issues.
     */
    fun <T> create(collectionName: String, dto: Dto<T>): String

    /**
     * Updates an existing document in the specified Firestore collection with the provided DTO.
     * The DTO's ID is validated before updating the document.
     *
     * @param collectionName The name of the Firestore collection.
     * @param dto The DTO to update the document with.
     *
     * @throws CorruptedFileException If the DTO cannot be inserted due to validation issues.
     * @throws ValidatorNotFoundException If no validation strategy is registered for the given DTO type.
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