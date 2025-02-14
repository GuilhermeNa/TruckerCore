package com.example.truckercore.infrastructure.database.firebase.util

import com.example.truckercore.configs.app_constants.Collection
import com.example.truckercore.infrastructure.database.firebase.errors.FirebaseRequestException
import com.example.truckercore.shared.interfaces.Dto
import com.example.truckercore.shared.utils.parameters.DocumentParameters
import com.example.truckercore.shared.utils.parameters.QueryParameters
import com.example.truckercore.shared.utils.parameters.SearchParameters

/**
 * A class responsible for making requests to Firebase Firestore with a specific collection, parameters, and DTO class.
 * This class manages the request's collection, parameters, and specifies whether it should observe the data stream.
 *
 * @param collection The Firestore collection to query or access.
 * @param clazz The class type of the DTO (Data Transfer Object) that represents the data in the collection.
 * @param params The parameters (either document or query parameters) that define the query or document-specific request.
 *
 */
internal class FirebaseRequest<T : Dto> private constructor(
    val collection: Collection,
    val clazz: Class<T>,
    val params: SearchParameters
) {

    companion object {
        /**
         * Factory method to create a new [Builder] instance.
         *
         * @param clazz The class type of the DTO to use in the request.
         * @return [Builder] A new instance of the builder to construct the [FirebaseRequest].
         */
        fun <T : Dto> create(clazz: Class<T>) = Builder(clazz)
    }

    /**
     * Builder pattern class for constructing a [FirebaseRequest].
     * It allows the setting of the collection and parameters before building the request.
     */
    class Builder<T : Dto>(private val clazz: Class<T>) {

        private var params: SearchParameters? = null
        private var collection: Collection? = null

        /**
         * Sets the parameters for the request.
         *
         * @param newParams The parameters to use for the search.
         * @return [Builder] The current builder instance to allow method chaining.
         */
        fun setParams(newParams: SearchParameters) = apply { this.params = newParams }

        /**
         * Sets the collection for the request.
         *
         * @param newCollection The Firestore collection to use for the request.
         * @return [Builder] The current builder instance to allow method chaining.
         */
        fun setCollection(newCollection: Collection) = apply { this.collection = newCollection }

        /**
         * Builds the [FirebaseRequest] using the parameters provided.
         *
         * @return [FirebaseRequest] The constructed Firebase request.
         * @throws IllegalArgumentException If required parameters like `collection` or `params` are missing.
         */
        fun build(): FirebaseRequest<T> {
            val validCollection = checkCollection()
            val validParams = checkParams()

            return FirebaseRequest(
                collection = validCollection,
                clazz = clazz,
                params = validParams
            )
        }

        /**
         * Validates that the parameters are set. If not, throws an [IllegalArgumentException].
         *
         * @return [SearchParameters] The validated parameters.
         * @throws IllegalArgumentException If no parameters are provided.
         */
        private fun checkParams(): SearchParameters =
            params ?: throw FirebaseRequestException("You must provide a Search Parameter.")

        /**
         * Validates that the collection is set. If not, throws an [IllegalArgumentException].
         *
         * @return [Collection] The validated collection.
         * @throws IllegalArgumentException If no collection is provided.
         */
        private fun checkCollection(): Collection =
            collection ?: throw FirebaseRequestException("You must provide a Collection reference.")
    }

    /**
     * Retrieves the parameters specific to a document query.
     *
     * @return [DocumentParameters] The parameters related to a document request.
     * @throws FirebaseRequestException If the parameters are of type [QueryParameters] instead of [DocumentParameters].
     */
    fun getDocumentParams(): DocumentParameters =
        if (params is DocumentParameters) params
        else throw FirebaseRequestException(
            "Expected a DocumentParameters but received a QueryParameters."
        )

    /**
     * Retrieves the parameters specific to a query request.
     *
     * @return [QueryParameters] The parameters related to a query request.
     * @throws FirebaseRequestException If the parameters are of type [DocumentParameters] instead of [QueryParameters].
     */
    fun getQueryParams(): QueryParameters =
        if (params is QueryParameters) params
        else throw FirebaseRequestException(
            "Expected a QueryParameters but received a DocumentParameters."
        )

    /**
     * Determines whether the request should stream (observe changes) based on the parameters.
     *
     * @return [Boolean] True if streaming is enabled (via [SearchParameters.shouldStream]), false otherwise.
     */
    fun shouldObserve() = params.shouldStream

}
