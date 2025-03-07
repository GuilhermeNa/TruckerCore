package com.example.truckercore.model.shared.utils.parameters

import com.example.truckercore.model.modules.user.entity.User
import com.example.truckercore.model.shared.errors.search_params.IllegalQueryParametersException

/**
 * A class representing the parameters for a query-based search.
 *
 * This class encapsulates the parameters required to perform a search using one or more queries.
 * It includes the user making the request, whether or not the results should be streamed,
 * and a list of `QuerySettings` that define the search criteria.
 * The class ensures that at least one query is provided when creating the `QueryParameters` instance.
 *
 * These parameters will be sent to the repositories for searching documents based on the specified criteria.
 * The repositories will use the `queries` to filter the results and return the documents that match the defined parameters.
 *
 * @param user The user making the search request.
 * @param shouldStream A boolean indicating whether the results should be streamed.
 * @param queries A list of `QuerySettings` that define the search criteria.
 * @throws IllegalQueryParametersException If no queries are provided when building the query parameters.
 */
class QueryParameters private constructor(
    override val user: User,
    override val shouldStream: Boolean,
    vararg val queries: QuerySettings
) : SearchParameters {

    init {
        if (queries.isEmpty()) {
            throw IllegalQueryParametersException(
                "You must provide at least one query before build a QueryParameter."
            )
        }
    }

    companion object {

        /**
         * Factory method to create a new [Builder] instance for constructing a [QueryParameters] object.
         *
         * @param user The user making the request.
         * @return A [Builder] instance to construct a [QueryParameters] object.
         */
        fun create(user: User) = Builder(user)

    }

    /**
     * A builder class to construct a [QueryParameters] instance.
     *
     * The builder allows for flexible construction of a [QueryParameters] object, enabling the user to
     * set the queries and the stream flag before the object is built.
     *
     * @param user The user making the request. It is required when building the parameters.
     */
    class Builder(val user: User) {

        private var shouldStream: Boolean = false
        private val queries = mutableListOf<QuerySettings>()

        /**
         * Sets the queries for the search.
         *
         * @param newQueries One or more `QuerySettings` to be used as search criteria.
         * @return The [Builder] instance to allow for method chaining.
         */
        fun setQueries(vararg newQueries: QuerySettings) = apply {
            queries.clear()
            queries.addAll(newQueries)
        }

        /**
         * Sets whether the search result should be streamed.
         *
         * @param shouldStream A boolean indicating if the result should be streamed.
         * @return The [Builder] instance to allow for method chaining.
         */
        fun setStream(shouldStream: Boolean) = apply { this.shouldStream = shouldStream }

        /**
         * Builds and returns a [QueryParameters] instance with the set parameters.
         *
         * @return A new [QueryParameters] object with the user, queries, and stream flag.
         * @throws IllegalQueryParametersException If no queries are provided, an exception is thrown.
         */
        fun build(): QueryParameters = QueryParameters(user, shouldStream, *queries.toTypedArray())

    }

}