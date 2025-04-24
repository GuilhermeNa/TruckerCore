/*
package com.example.truckercore.model.shared.utils.parameters

import com.example.truckercore.model.modules.user.data.User
import com.example.truckercore.model.shared.errors.search_params.IllegalDocumentParametersException

*/
/**
 * A class representing the parameters for a document search.
 *
 * This class encapsulates the parameters required to search for documents. It includes the user making the request,
 * the document ID, and whether or not the result should be streamed.
 * The class ensures that a valid ID is provided when creating a `DocumentParameters` instance.
 *
 * These parameters will be sent to the repositories for the document search, where the repository will
 * perform the search based on the criteria defined here (i.e., `id` and `shouldStream`).
 *
 * @param user The user making the document search request.
 * @param id The ID of the document to search for.
 * @param shouldStream A boolean indicating whether the result should be streamed.
 * @throws IllegalDocumentParametersException If the ID is blank when building the document parameters.
 *//*

class DocumentParameters private constructor(
    override val user: User,
    val id: String,
    override val shouldStream: Boolean
) : SearchParameters {

    init {

        if (id.isBlank()) throw IllegalDocumentParametersException(
            "You must provide the ID before build a DocumentParameter."
        )

    }

    companion object {

        */
/**
         * Factory method to create a new [Builder] instance for constructing a [DocumentParameters] object.
         *
         * @param user The user making the request.
         * @return A [Builder] instance to construct a [DocumentParameters] object.
         *//*

        fun create(user: User) = Builder(user)

    }

    */
/**
     * A builder class to construct a [DocumentParameters] instance.
     *
     * The builder allows for flexible construction of a [DocumentParameters] object, enabling the user to
     * set the document ID and the stream flag before the object is built.
     *
     * @param user The user making the request. It is required when building the parameters.
     *//*

    class Builder(val user: User) {

        private var id: String = ""
        private var shouldStream: Boolean = false

        */
/**
         * Sets the document ID for the parameters.
         *
         * @param newId The document ID to be used for the search.
         * @return The [Builder] instance to allow for method chaining.
         *//*

        fun setId(newId: String) = apply { this.id = newId }

        */
/**
         * Sets whether the search result should be streamed.
         *
         * @param shouldStream A boolean indicating if the result should be streamed.
         * @return The [Builder] instance to allow for method chaining.
         *//*

        fun setStream(shouldStream: Boolean) = apply { this.shouldStream = shouldStream }

        */
/**
         * Builds and returns a [DocumentParameters] instance with the set parameters.
         *
         * @return A new [DocumentParameters] object with the user, ID, and stream flag.
         * @throws IllegalDocumentParametersException If the ID is blank, an exception is thrown.
         *//*

        fun build(): DocumentParameters = DocumentParameters(user, this.id, shouldStream)

    }

}

*/
